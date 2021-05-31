package com.cxynw.dao.base;

import java.util.Optional;

public interface BaseUpdate<T,ID> {

    Optional<T> update(T t);

}
