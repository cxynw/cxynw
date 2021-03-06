package com.cxynw.controller.api;

import com.cxynw.model.does.FileMark;
import com.cxynw.model.does.User;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.FileTypeEnum;
import com.cxynw.model.param.UploadParam;
import com.cxynw.model.param.AccountParam;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.model.vo2.BaseVO;
import com.cxynw.service.AccountService;
import com.cxynw.service.FileMarkService;
import com.cxynw.service.UserService;
import com.cxynw.model.enums.DataTypeEnum;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;

@Slf4j
@RestController
public class BaseApiController {

    private final UserService userService;
    private final FileMarkService fileMarkService;
    private final AccountService accountService;

    public BaseApiController(UserService userService,
                             FileMarkService fileMarkService, AccountService accountService) {
        this.userService = userService;
        this.fileMarkService = fileMarkService;
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public BaseSuccessResponse register(@RequestBody @Valid AccountParam accountParam){
        return userService.createNewUser(accountParam);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload/avatar")
    public BaseVO uploadAvatar(@RequestParam("avatar") MultipartFile avatar){
        if(log.isDebugEnabled()){
            log.debug("start upload avatar");
        }

        FileMark fileMark = fileMarkService.uploadFiles(avatar, FileTypeEnum.AVATAR);
        User user = accountService.getCurrentAccount().get();

        boolean b = userService.setAvatarById(fileMark.getFileMarkId(), user.getUserId());
        if(b){
            return new BaseVO(CodeEnum.SUCCEED,"????????????????????????");
        }
        return new BaseVO(CodeEnum.FAIL);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiOperation("????????????????????????")
    @PostMapping("/upload")
    public BaseVO<FileInfo[]> upload(
            @Valid UploadParam uploadParam,
            @RequestParam("files") MultipartFile[] files,
            HttpServletResponse response
    ){
        switch (uploadParam.getDataType().toUpperCase()){
            case DataTypeEnum.IMAGE:{
                List<FileMark> fileMarkList = fileMarkService.uploadFiles(files, FileTypeEnum.IMAGE);
                FileInfo[] wg_images = new FileInfo[fileMarkList.size()];
                for (int i = 0; i < fileMarkList.size(); i++) {
                    FileMark item = fileMarkList.get(i);
                    FileInfo wg_image = new FileInfo();
                    wg_image.setUrl(
                            String.format("/image/%s_%s.%s",item.getSha256(),item.getFileMarkId(),item.getExtension())
                    );
                    wg_images[i] = wg_image;
                }
                return new BaseVO<>(CodeEnum.SUCCEED.value(), "??????????????????", wg_images);
            }
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new BaseVO<>(CodeEnum.FAIL,"??????????????????????????????");
    }


    @Data
    static class FileInfo{
        private String url;
    }

}
