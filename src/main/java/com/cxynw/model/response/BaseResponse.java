package com.cxynw.model.response;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    private int code;
    private String message;

}
