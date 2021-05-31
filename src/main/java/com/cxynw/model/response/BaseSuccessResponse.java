package com.cxynw.model.response;

import com.cxynw.model.enums.CodeEnum;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseSuccessResponse<T> extends BaseResponse{

    public BaseSuccessResponse(int code, String message) {
        this(code,message,null);
    }

    public BaseSuccessResponse(CodeEnum code, String message){
        this(code.value(),message,null);
    }

    public BaseSuccessResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public BaseSuccessResponse(CodeEnum code, String message, T data) {
        super(code.value(), message);
        this.data = data;
    }

    private T data;

}
