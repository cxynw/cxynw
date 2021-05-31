package com.cxynw.dao.impl;

import com.cxynw.dao.PostCommentDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.PostComment;
import com.cxynw.repository.PostCommentRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Repository
public class PostCommentDaoImpl extends BaseDaoImpl<PostComment, BigInteger> implements PostCommentDao {

    public PostCommentDaoImpl(@NotNull PostCommentRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected BigInteger getId(PostComment postComment) {
        return postComment.getPostCommentId();
    }

}
