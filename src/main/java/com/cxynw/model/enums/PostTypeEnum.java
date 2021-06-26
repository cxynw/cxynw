package com.cxynw.model.enums;

public enum PostTypeEnum {

    PUBLIC_ARTICLE(1), //公开的文章
    PRIVATE_ARTICLE(2); //私人文章，只能自己查看

    private Integer value;

    PostTypeEnum(Integer type){
        this.value = type;
    }

    public Integer getValue() {
        return value;
    }
}
