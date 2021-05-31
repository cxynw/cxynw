package com.cxynw.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BasePageVO<T> extends BaseVO<T>{

    private int nextPage;
    private int PreviousPage;
    private boolean hasNext;
    private boolean hasPrevious;

}
