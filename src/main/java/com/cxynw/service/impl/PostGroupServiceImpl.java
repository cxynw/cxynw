package com.cxynw.service.impl;

import com.cxynw.dao.PostGroupDao;
import com.cxynw.model.does.PostGroup;
import com.cxynw.repository.base.BaseRepository;
import com.cxynw.service.PostGroupService;
import com.cxynw.service.base.AbstractCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
public class PostGroupServiceImpl extends AbstractCrudService<PostGroup, BigInteger> implements PostGroupService {

    private final PostGroupDao dao;

    protected PostGroupServiceImpl(BaseRepository<PostGroup, BigInteger> repository,
                                   PostGroupDao dao) {
        super(repository);
        this.dao = dao;
    }

    @Override
    @Transactional
    public Page<PostGroup> page(Pageable pageable) {
        Page<PostGroup> page = dao.findAll(pageable);
        return page;
    }

}
