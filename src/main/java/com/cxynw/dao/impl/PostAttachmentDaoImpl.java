package com.cxynw.dao.impl;

import com.cxynw.dao.PostAttachmentDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.PostAttachment;
import com.cxynw.repository.PostAttachmentRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Repository
public class PostAttachmentDaoImpl extends BaseDaoImpl<PostAttachment, BigInteger> implements PostAttachmentDao {

    public PostAttachmentDaoImpl(@NotNull PostAttachmentRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected BigInteger getId(PostAttachment postAttachment) {
        return postAttachment.getPostId();
    }
}
