package com.cxynw.model.enums;

import lombok.Getter;

@Getter
public enum LogTypeEnum {

    /**
     * Logged in
     */
    LOGGED_IN(0l),

    /**
     * Logged out
     */
    LOGGED_OUT(1l),

    USERNAME_NOT_FOUND(2l),

    USER_REGISTER(3l),

    FILE_DOWNLOAD_FAIL(4l),
    LOGGED_FAIL(5l),
    PARAM_BING_ERROR(6l),
    THROWABLE(7l),
    HTTP_REQUEST_RECORD_ERROR(8l),
    COOKIE_THEFT_EXCEPTION(9L),
    UNAUTHORIZED(10L);

    private final Long value;

    LogTypeEnum(Long value) {
        this.value = value;
    }

}
