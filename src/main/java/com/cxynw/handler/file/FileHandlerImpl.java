package com.cxynw.handler.file;

import com.cxynw.model.does.User;
import com.cxynw.model.does.FileMark;
import com.cxynw.model.enums.FileTypeEnum;
import com.cxynw.utils.FileUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class FileHandlerImpl implements FileHandler {

    @Override
    public UploadFile upload(@NonNull MultipartFile multipartFile, User uploader, FileTypeEnum fileTypeEnum)
            throws IOException, NoSuchAlgorithmException {
        Assert.notNull(multipartFile,"multipart fileMark must not be null");

        String basename = FileUtils.getBasename(multipartFile.getOriginalFilename());
        Optional<String> extension = FileUtils.getExtension(multipartFile.getOriginalFilename());
        BigInteger fileSize = BigInteger.valueOf(multipartFile.getSize());
        Long size = multipartFile.getSize();

        if(log.isDebugEnabled()){
            log.debug("basename: {} extension: {} fileMark size: {}",basename,extension,fileSize);
        }

        FileUtils.UploadFile uploadFile = FileUtils.sha256(multipartFile.getInputStream());



        FileMark fileMark = new FileMark();
        fileMark.setFileType(fileTypeEnum.getValue());
        fileMark.setFileBasename(basename);
        fileMark.setExtension(extension.orElseGet(()-> null));
        fileMark.setUploader(uploader);
        fileMark.setFileSize(size);
        fileMark.setSha256(uploadFile.getSha512());

        UploadFile result = new UploadFile(
                uploadFile.getTempLocation(),
                uploadFile.getSha512(),
                fileMark
        );
        return result;
    }

    @Override
    public List<UploadFile> upload(@NonNull MultipartFile[] multipartFiles, User uploader,FileTypeEnum fileTypeEnum) throws IOException, NoSuchAlgorithmException {
        ArrayList<UploadFile> uploadFiles = new ArrayList<>(multipartFiles.length);
        for (MultipartFile item :
                multipartFiles) {
            UploadFile uploadFile = upload(item, uploader,fileTypeEnum);
            uploadFiles.add(uploadFile);
        }
        return uploadFiles;
    }

}
