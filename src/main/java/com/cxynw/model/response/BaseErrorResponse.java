package com.cxynw.model.response;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BaseErrorResponse extends BaseResponse{

    public BaseErrorResponse(int code, String message) {
        super(code, message);
    }

}
