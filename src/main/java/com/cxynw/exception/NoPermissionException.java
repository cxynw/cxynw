package com.cxynw.exception;

public class NoPermissionException extends RuntimeException{

    public NoPermissionException() {
        this("缺少权限");
    }

    public NoPermissionException(String message) {
        super(message);
    }
}
