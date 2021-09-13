package com.cxynw.model.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum PostTypeEnum {

    PUBLIC_ARTICLE(1,"公开"), //公开的文章
    PRIVATE_ARTICLE(2,"私密"); //私人文章，只能自己查看

    private Integer value;
    private String name;

    PostTypeEnum(Integer type,String name){
        this.value = type;
        this.name = name;
    }

}
