package com.cxynw.dao.impl;

import com.cxynw.dao.PostDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.Post;
import com.cxynw.repository.PostRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Repository
public class PostDaoImpl extends BaseDaoImpl<Post, BigInteger> implements PostDao {

    public PostDaoImpl(@NotNull PostRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected BigInteger getId(Post post) {
        return post.getPostId();
    }
}
