package com.cxynw.exception.api;

import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.response.BaseErrorResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class BaseApiRuntimeException extends RuntimeException{

    private CodeEnum errorCode;
    private String responseMessage;

    public BaseApiRuntimeException(CodeEnum errorCode, String responseMessage){
        this.errorCode = errorCode;
        this.responseMessage = responseMessage;
    }

    public BaseErrorResponse convertToBaseResponse(){
        return new BaseErrorResponse(errorCode.value(),responseMessage);
    }

}
