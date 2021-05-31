package com.cxynw.dao.base;

import java.util.Optional;

public interface BaseInsertDao<T> {

    /**
     * @throws java.sql.SQLException
     * @param t
     * @return
     */
    Optional<T> insert(T t);

}
