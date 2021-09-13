package com.cxynw.model.vo2;

import lombok.*;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class BasePageVO<T> extends BaseVO<T>{

    private Integer nextPage;
    private Integer PreviousPage;
    private Boolean hasNext;
    private Boolean hasPrevious;

}
