package com.cxynw.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@ToString
@Data
public class PostSearchQuery {

    @NotBlank
    @Max(value = 64,message = "暂时不支持超过64字符的检索")
    private String keywords;

}
