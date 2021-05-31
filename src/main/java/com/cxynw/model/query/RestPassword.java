package com.cxynw.model.query;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class RestPassword {

    @Size(min = 32,max = 32,message = "无效密码")
    private String password;

}
