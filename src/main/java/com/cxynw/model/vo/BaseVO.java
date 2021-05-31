package com.cxynw.model.vo;

import com.cxynw.model.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseVO<T> {

    private int statusCode;
    private String message;
    private T data;

    public BaseVO(CodeEnum codeEnum){
        this.statusCode = codeEnum.value();
        this.message = codeEnum.message();
    }

    public BaseVO(CodeEnum codeEnum,String message){
        this.statusCode = codeEnum.value();
        this.message = message;
    }

}
