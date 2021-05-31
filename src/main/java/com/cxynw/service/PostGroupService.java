package com.cxynw.service;

import com.cxynw.model.does.PostGroup;
import com.cxynw.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public interface PostGroupService extends CrudService<PostGroup, BigInteger> {

    Page<PostGroup> page(Pageable pageable);

}
