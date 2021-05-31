package com.cxynw.service.impl;

import com.cxynw.manager.PostAttachmentCacheDao;
import com.cxynw.model.does.PostAttachment;
import com.cxynw.service.PostAttachmentService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {

    private final PostAttachmentCacheDao attachmentCacheDao;

    public PostAttachmentServiceImpl(PostAttachmentCacheDao attachmentCacheDao) {
        this.attachmentCacheDao = attachmentCacheDao;
    }

    @Override
    public List<PostAttachment> findByPostId(BigInteger id) {
        return attachmentCacheDao.findAllByPostId(id);
    }

}
