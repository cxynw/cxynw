package com.cxynw.controller.api;

import com.cxynw.exception.api.FileDownloadException;
import com.cxynw.model.does.FileMark;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.FileTypeEnum;
import com.cxynw.model.query.FileInfoImpl;
import com.cxynw.service.FileService;
import com.cxynw.utils.AvatarUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

@Slf4j
@RestController
@Transactional
public class FileDownloadController {

    private final FileService fileService;

    public FileDownloadController(FileService fileService) {
        this.fileService = fileService;
    }


    private void setCacheHeader(HttpServletResponse response){
        response.setHeader("Cache-Control","");
    }

    @ApiOperation("系统自动帮助没有设置头像的用户设置头像")
    @GetMapping("/avatar/generate/{nickname}")
    public void avatar(@PathVariable("nickname")String nickname,
                       HttpServletResponse response)
            throws IOException {
        response.setContentType("image/jpeg");
        BufferedImage avatar = AvatarUtils.generateFaceByUsername(nickname);
        ImageIO.write(avatar,"jpg",response.getOutputStream());
    }


    /**
     * 头像下载，开启事务
     *
     * @param id
     * @param extension
     * @param password
     * @param response
     * @throws IOException
     */
    @ApiOperation("头像下载接口")
    @GetMapping(value = {"/avatar/{id:[0-9]+}",})
    public void avatar(
//            @PathVariable("sha256") String sha256,
            @PathVariable("id")BigInteger id,
//            @PathVariable(value = "extension",required = false)String extension,
//            @RequestParam(value = "password",required = false)String password,
            HttpServletResponse response) throws IOException {

        if(log.isDebugEnabled()){
//            log.debug("开始下载头像： id:{} extension:{} password:{}",id,extension,password);
        }

        Optional<FileMark> optional = fileService.findById(id);
        if(optional.isEmpty()){
            return;
        }
        FileMark fileMark = optional.get();

//        if(check(fileMark,password, FileTypeEnum.AVATAR) == false){
//            return;
//        }

        fileService.autoResponseFile(new FileInfoImpl(fileMark),response);
    }

    /**
     *
     * @param fileMark
     * @param downloadPassword
     * @param fileTypeEnum
     * @return true is check success
     */
    private boolean check(FileMark fileMark,String downloadPassword,FileTypeEnum fileTypeEnum){
        boolean result = checkExecute(fileMark, downloadPassword, fileTypeEnum);
        if(log.isDebugEnabled()){
            log.debug("file basename:{} file password:{} download password:{} file type:{}",fileMark.getFileBasename(),fileMark.getDownloadPassword(),downloadPassword,fileTypeEnum);
            if(result){
                log.debug("下载请求的参数校验成功，该文件可以下载");
            }else{
                log.debug("下载请求的参数校验失败，该文件禁止下载");
                throw new FileDownloadException(CodeEnum.FILE_DOWNLOAD_EXCEPTION,"密码错误或者文件不存在");
            }
        }
        return result;
    }

    private boolean checkExecute(FileMark fileMark,String downloadPassword,FileTypeEnum fileTypeEnum){
        if(fileMark.getFileType().equals(fileTypeEnum.getValue()) == false){
            return false;
        }
        if(fileMark.getDownloadPassword() == null){
            return true;
        }
        if(BCrypt.checkpw(downloadPassword,fileMark.getDownloadPassword())){
            return true;
        }
        return false;
    }

    /**
     * 图片下载，开启事务
     *
     * @param id
     * @param extension
     * @param password
     * @param response
     * @throws IOException
     */
    @ApiOperation("图片下载接口")
    @GetMapping(value = {"/image/{sha256}_{id:[0-9]+}.{extension}"})
    public void image(
            @PathVariable("sha256") String sha256,
            @PathVariable("id")BigInteger id,
            @PathVariable(value = "extension",required = false)String extension,
            @RequestParam(value = "password",required = false)String password,
            HttpServletResponse response) throws IOException {

        if(log.isDebugEnabled()){
            log.debug("开始下载附件： id:{} extension:{} password:{}",id,extension,password);
        }

        Optional<FileMark> optional = fileService.findById(id);
        if(optional.isEmpty()){
            return;
        }
        FileMark fileMark = optional.get();

        if(check(fileMark,password,FileTypeEnum.IMAGE) == false){
            return;
        }



        fileService.autoResponseFile(new FileInfoImpl(fileMark),response);
    }

    /**
     * 附件下载，开启事务
     *
     * @param id
     * @param extension
     * @param password
     * @param response
     */
    @ApiOperation("附件下载接口")
    @GetMapping(value = {"/attachment/{sha256}_{id:[0-9]+}.{extension}"})
    public void attachment(
            @PathVariable("sha256") String sha256,
            @PathVariable("id")BigInteger id,
            @PathVariable(value = "extension",required = false)String extension,
            @RequestParam(value = "password",required = false)String password,
            HttpServletResponse response) throws IOException {

        Optional<FileMark> optional = fileService.findById(id);

        if(optional.isEmpty()){
            return;
        }

        final FileMark fileMark = optional.get();

        if(check(fileMark,password,FileTypeEnum.ATTACHMENT) == false){
            return;
        }


        FileInfoImpl fileInfo = new FileInfoImpl(fileMark);
        fileService.setDownloadHeader(fileInfo,response);

        fileService.autoResponseFile(fileInfo,response);

    }

}
