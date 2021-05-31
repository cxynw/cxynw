package com.cxynw.controller.api.user.rest;

import com.cxynw.model.query.RestNickname;
import com.cxynw.model.query.RestPassword;
import com.cxynw.model.response.BaseResponse;
import com.cxynw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user/rest")
public class UserPasswordController {

    private final UserService userService;

    public UserPasswordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/password")
    public BaseResponse rest(@Valid @RequestBody RestPassword restPassword){
        return userService.restPassword(restPassword);
    }

    @PostMapping("/nickname")
    public BaseResponse nickName(@Valid @RequestBody RestNickname restNickname){
        return userService.restNickname(restNickname);
    }

}
