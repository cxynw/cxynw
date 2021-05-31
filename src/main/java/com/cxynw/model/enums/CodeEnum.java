package com.cxynw.model.enums;

public enum CodeEnum {
    FAIL(0,"操作失败"),
    ERROR(0,"发生错误"),
    SUCCEED(1,"操作成功"),
    LOGGED_FAIL(2,""),
    BIND_ERROR(3,"绑定错误"),
    SIGN_IN_FAIL(4,""),
    SIGN_IN_SUCCEED(5,""),
    LOGOUT_SUCCEED(6,""),
    LOGGED_SUCCEED(7,""),
    RELEASE_POST_SUCCEED(8,""),
    FILE_DOWNLOAD_EXCEPTION(9,""),
    DELETE_POST_NOT_EXISTS(10,""),
    DELETE_POST_SUCCEED(11,""),
    DELETE_POST_FAIL(12,""),
    POST_NOT_EXISTS(13,"");

    private final int value;
    private final String message;

    CodeEnum(int value,String message){
        this.value = value;
        this.message = message;
    }

    public int value(){
        return value;
    }

    public String message(){
        return message;
    }

}
