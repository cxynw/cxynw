package com.cxynw.dao.impl;

import com.cxynw.dao.PostGroupDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.PostGroup;
import com.cxynw.repository.PostGroupRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Repository
public class PostGroupDaoImpl extends BaseDaoImpl<PostGroup, BigInteger>
        implements PostGroupDao {

    public PostGroupDaoImpl(@NotNull PostGroupRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected BigInteger getId(PostGroup postGroup) {
        return postGroup.getPostGroupId();
    }
}
