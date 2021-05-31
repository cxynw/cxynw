package com.cxynw.model.enums;

import com.cxynw.model.does.Actor;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public enum RoleEnum {

    ADMIN(BigInteger.ONE),
    USER(BigInteger.TWO);

    private final Actor actor;

    RoleEnum(BigInteger id){
        actor = new Actor();
        actor.setActorId(id);
    }

}
