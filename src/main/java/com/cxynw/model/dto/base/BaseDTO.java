package com.cxynw.model.dto.base;

public interface BaseDTO<T> {

    boolean hasError();

    int getCode();

    String getErrorMessage();

    T getData();

}
