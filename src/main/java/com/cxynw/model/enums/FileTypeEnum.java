package com.cxynw.model.enums;

public enum FileTypeEnum {


    AVATAR(1), //头像
    IMAGE(2),//图片
    ATTACHMENT(3); // 附件

    private Integer value;

    FileTypeEnum(Integer type){
        this.value = type;
    }

    public Integer getValue() {
        return value;
    }
}
