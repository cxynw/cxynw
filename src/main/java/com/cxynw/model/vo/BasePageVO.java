package com.cxynw.model.vo;

import lombok.*;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class BasePageVO<T> extends BaseVO<T>{

    private int nextPage;
    private int PreviousPage;
    private boolean hasNext;
    private boolean hasPrevious;

}
