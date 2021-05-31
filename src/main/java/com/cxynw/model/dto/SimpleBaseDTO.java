package com.cxynw.model.dto;

import com.cxynw.model.dto.base.BaseDTO;
import com.cxynw.model.enums.CodeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@ToString
public class SimpleBaseDTO<T> implements BaseDTO {

    private boolean error;
    private CodeEnum code;
    private String errorMessage;
    private T data;

    public SimpleBaseDTO(boolean error,CodeEnum code,String errorMessage){
        this.error = error;
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public SimpleBaseDTO(boolean error,T data){
        this.error = error;
        if(error){
            this.code = CodeEnum.ERROR;
        }else{
            this.code = CodeEnum.SUCCEED;
        }
        this.data = data;
    }

    public SimpleBaseDTO(boolean error,T data,CodeEnum code){
        this.error = error;
        if(error){
            this.code = CodeEnum.ERROR;
        }else {
            this.code = CodeEnum.SUCCEED;
        }
        this.code = code;
        this.data = data;
    }

    @Override
    public boolean hasError() {
        return error;
    }

    @Override
    public int getCode() {
        return code.value();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public T getData() {
        return data;
    }

}
