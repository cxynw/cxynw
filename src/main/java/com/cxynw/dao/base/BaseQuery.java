package com.cxynw.dao.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseQuery<T,ID> {

    Optional<T> findById(ID id);

    Page<T> findAll(Pageable pageable);

    /**
     * 只要值不为空的，都算作查询条件
     *
     * @param t
     * @return
     */
    List<T> findByProperties(T t);

}
