package com.cxynw.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BasePageVo<T> extends BaseVo{

    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer nextPage;
    private Integer previousPage;

    private Collection<T> content;

    public void setPreviousPage(Integer previousPage){
        if(previousPage < 1){
            this.previousPage = 1;
        }else {
            this.previousPage = previousPage;
        }
    }

}
