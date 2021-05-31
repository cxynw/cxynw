package com.cxynw.exception.api;

import com.cxynw.model.enums.CodeEnum;

public class FileDownloadException extends BaseApiRuntimeException{


    public FileDownloadException(CodeEnum errorCode, String responseMessage) {
        super(errorCode, responseMessage);
    }

}
