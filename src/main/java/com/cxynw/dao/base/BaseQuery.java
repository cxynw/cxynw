package com.cxynw.dao.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseQuery<T,ID> {

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    Optional<T> findById(ID id);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 只要值不为空的，都算作查询条件
     *
     * @param t
     * @return
     */
    List<T> findByProperties(T t);

}
