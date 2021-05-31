package com.cxynw.manager;

import com.cxynw.dao.PostAttachmentDao;
import com.cxynw.model.does.PostAttachment;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class PostAttachmentCacheDaoImpl implements PostAttachmentCacheDao {

    private final PostAttachmentDao dao;

    public PostAttachmentCacheDaoImpl(PostAttachmentDao dao) {
        this.dao = dao;
    }

    @Override
    public Optional<PostAttachment> findById(BigInteger id) {
        return dao.findById(id);
    }

    @Override
    public Optional<PostAttachment> deleteById(BigInteger id) {
        Optional<PostAttachment> entity = this.dao.findById(id);
        if(entity.isEmpty()){
            return Optional.empty();
        }
        dao.deleteById(id);

        return entity;
    }

    @Override
    public List<PostAttachment> findAllByPostId(BigInteger postId) {
        PostAttachment attachment = new PostAttachment();
        attachment.setPostId(postId);
        return dao.findByProperties(attachment);
    }

    @Override
    public Optional<PostAttachment> insert(PostAttachment postAttachment) {
        return dao.insert(postAttachment);
    }
}
