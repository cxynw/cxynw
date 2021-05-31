package com.cxynw.model.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class PostCommentQuery {

    @NotNull
    private BigInteger postId;
    @NotNull
    private Integer page;

    public Integer getPage(){
        if(page < 1){
            return 1;
        }
        return page;
    }

}
