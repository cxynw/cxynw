package com.cxynw.model.query;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class RestNickname {

    @Size(min =1,max = 64,message = "无效昵称")
    private String nickname;

}
