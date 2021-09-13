package com.cxynw.model.vo2;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseItemVo<T> extends BasePageVO{

    protected Integer currentPage;
    protected Integer pageSize;
    protected T[] items;

    public void setHasPrevious(Boolean hasPrevious){
        super.setHasPrevious(hasPrevious);
        if(hasPrevious){
            super.setPreviousPage(currentPage -1);
        }
    }

    public void setHasNext(Boolean hasNext){
        super.setHasNext(hasNext);
        if(hasNext){
            super.setNextPage(currentPage+1);
        }
    }

}
