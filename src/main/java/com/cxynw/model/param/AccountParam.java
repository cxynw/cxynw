package com.cxynw.model.param;

import com.cxynw.model.dto.base.InputConverter;
import com.cxynw.model.does.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AccountParam implements InputConverter<User> {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 1,max = 64,message = "用户名的长度不能超过{max}，也能少于{min}")
    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "用户名只能由字母和数字组成")
    private String username;

    @NotBlank(message = "昵称不能为空")
    @Size(min = 1,max = 64,message = "昵称的长度不能超过{max}，也能少于{min}")
    private String nickname;

    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "密码不合法")
    @Size(min = 32,max = 32,message = "密码不合法")
    private String password;
    @Size(max = 128,message = "抱歉，我们不支持这长的邮箱")
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",message = "非法邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Override
    public User convertTo() {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(
                BCrypt.hashpw(password,BCrypt.gensalt()));
        user.setNickname(nickname);
        return user;
    }
}
