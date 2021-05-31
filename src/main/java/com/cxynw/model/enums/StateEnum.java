package com.cxynw.model.enums;

import lombok.Getter;

@Getter
public enum StateEnum {

    NORMAL(1); //状态正常

    private final Integer value;
    StateEnum(Integer value){ this.value = value; }

}
