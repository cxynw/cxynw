package com.cxynw.controller.api.user;

import com.cxynw.model.does.User;
import com.cxynw.model.vo2.Base;
import com.cxynw.model.vo2.UserVO;
import com.cxynw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username")
    public ResponseEntity<Base<UserVO>> username(@RequestParam(value = "email",required = false)String email){
        Optional<User> user = userService.findByEmail(email);
        Base<UserVO> base = new Base<>();
        if(user.isEmpty()){
            base.setMessage("not found email");
            log.info("根据邮箱找回用户名无效，无效邮箱 [{}]",email);
            return new ResponseEntity<>(base,HttpStatus.NOT_FOUND);
        }else{
            base.setMessage("success");
            UserVO vo = new UserVO();
            User u = user.get();
            vo.setUsername(u.getUsername());
            vo.setNickname(u.getNickname());
            vo.setEmail(u.getEmail());
            base.setData(vo);
            log.info("根据邮箱找回用户名成功，邮箱 [{}] 用户名 [{}] 昵称[{}]",email,vo.getUsername(),vo.getNickname());
            return new ResponseEntity<>(base,HttpStatus.OK);
        }
    }

}
