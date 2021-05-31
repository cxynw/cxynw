package com.cxynw.model.enums;

public enum PermissionTypeEnum {


    MENU(1); // 菜单

    private Integer value;

    PermissionTypeEnum(Integer type){
        this.value = type;
    }

    public Integer value() {
        return value;
    }
}
