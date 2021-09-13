package com.cxynw.service.impl;

import com.cxynw.exception.UnKnownException;
import com.cxynw.exception.UploadFileException;
import com.cxynw.handler.file.FileHandler;
import com.cxynw.manager.FileMarkCacheDao;
import com.cxynw.model.does.User;
import com.cxynw.model.does.FileMark;
import com.cxynw.model.enums.FileTypeEnum;
import com.cxynw.model.query.FileInfo;
import com.cxynw.model.vo2.FileMarkItemVo;
import com.cxynw.repository.FileMarkRepository;
import com.cxynw.service.AccountService;
import com.cxynw.service.FileMarkService;
import com.cxynw.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FileMarkServiceImpl extends AbstractCrudService<FileMark, BigInteger> implements FileMarkService {

    private final java.io.File uploadDir = new java.io.File("files");
    private final FileHandler fileHandler;
    private final AccountService accountService;
    private final FileMarkCacheDao fileMarkCacheDao;
    private final FileMarkRepository fileMarkRepository;

    private FileMarkService fileMarkService;

    @Autowired
    public void setFileService(FileMarkService fileMarkService){
        this.fileMarkService = fileMarkService;
    }

    public FileMarkServiceImpl(FileMarkRepository repository,
                               FileHandler fileHandler,
                               AccountService accountService, FileMarkCacheDao fileMarkCacheDao, FileMarkRepository fileMarkRepository) {
        super(repository);
        this.fileHandler = fileHandler;
        this.accountService = accountService;
        this.fileMarkCacheDao = fileMarkCacheDao;
        this.fileMarkRepository = fileMarkRepository;
        if(!uploadDir.isDirectory()){
            uploadDir.mkdirs();
        }
    }

    @Transactional
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Override
    public List<FileMark> uploadFiles(MultipartFile[] files, FileTypeEnum fileTypeEnum){
        Optional<User> optional = accountService.getCurrentAccount();
        if(optional.isEmpty()){
            throw new UnKnownException();
        }
        User user = optional.get();

        List<FileHandler.UploadFile> uploadFileList;
        try {
            uploadFileList = fileHandler.upload(files, user,fileTypeEnum);
        } catch (IOException | NoSuchAlgorithmException exception) {
            throw new UploadFileException(exception.getMessage());
        }

        ArrayList<FileMark> result = new ArrayList<>(files.length);
        for (FileHandler.UploadFile file:
                uploadFileList) {
            String sha256 = file.getSha256();
            //如果已经存储了一样的文件，并且该文件存在本机目录
            java.io.File saveLocation = new java.io.File(uploadDir,file.getSha256());
            if(fileMarkRepository.existsBySha256(sha256) && saveLocation.isFile()){
                file.getTempLocation().delete();
            }else{
                if(log.isDebugEnabled()){
                    log.debug("rename to {}",saveLocation);
                }
                file.getTempLocation().renameTo(saveLocation);
            }
            FileMark pojo = fileMarkCacheDao.insert(file.getUserFile()).get();
            result.add(pojo);
        }
        return result;
    }

    @Transactional
    @Override
    public FileMark uploadFiles(MultipartFile file, FileTypeEnum fileTypeEnum) {
        MultipartFile[] files = new MultipartFile[]{file};
        return fileMarkService.uploadFiles(files,fileTypeEnum).get(0);
    }

    @Override
    public void autoResponseFile(FileInfo fileInfo, HttpServletResponse response) {
        try(FileInputStream input = new FileInputStream(new File("files", fileInfo.getSha256()))) {
            byte[] data = new byte[1024 * 5];
            int n;
            ServletOutputStream outputStream = response.getOutputStream();
            while ((n = input.read(data)) != -1) {
                outputStream.write(data, 0, n);
            };
            fileMarkRepository.updateDownloadTimesById(fileInfo.getDownloadTimes()+1
                    ,fileInfo.getFileId());
            outputStream.flush();
        } catch (FileNotFoundException exception){
            log.warn("本地储存文件丢失");
        }catch (IOException exception) {
            if(log.isDebugEnabled()){
                log.debug(exception.getMessage());
            }
            throw new RuntimeException("文件传输过程中发生错误");
        }
    }

    @Override
    public void setDownloadHeader(FileInfo fileInfo, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        try {
            String fileName = fileInfo.getFileBasename()+"."+fileInfo.getFileExtension();
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("文件传输过程中发生错误");
        }
        response.setContentLengthLong(fileInfo.getFileSize());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public FileMarkItemVo findAll(PageRequest pageRequest) {
        Page<FileMark> fileMarkPage = fileMarkRepository.findAll(pageRequest);
        return FileMarkItemVo.generate(fileMarkPage);
    }

}
