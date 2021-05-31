package com.cxynw.service;

import com.cxynw.model.does.PostAttachment;

import java.math.BigInteger;
import java.util.List;

public interface PostAttachmentService {

    List<PostAttachment> findByPostId(BigInteger id);

}
