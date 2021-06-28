package com.cxynw.model.vo;

import lombok.Data;

@Data
public class BaseItemVo<T> {

    protected Integer currentPage;
    protected Boolean hasPrevious;
    protected Integer previousPage; // 这个是自动设置的
    protected Boolean hasNext;
    protected Integer nextPage; // 这个是自动设置的
    protected T[] items;

    public void setHasPrevious(Boolean hasPrevious){
        this.hasPrevious = hasPrevious;
        if(hasPrevious){
            this.previousPage = currentPage -1;
        }
    }

    public void setHasNext(Boolean hasNext){
        this.hasNext = hasNext;
        if(hasNext){
            this.nextPage = currentPage+1;
        }
    }

}
