package com.cxynw.model.enums;

public enum PostTypeEnum {

    ARTICLE(1); //文章

    private Integer value;

    PostTypeEnum(Integer type){
        this.value = type;
    }

    public Integer getValue() {
        return value;
    }
}
