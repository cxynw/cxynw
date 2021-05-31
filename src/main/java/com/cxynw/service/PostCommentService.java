package com.cxynw.service;

import com.cxynw.model.does.Post;
import com.cxynw.model.does.PostComment;
import com.cxynw.model.query.PostCommentInsert;
import com.cxynw.model.vo.PostCommentVO;
import com.cxynw.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Optional;

public interface PostCommentService extends BaseService<PostComment,BigInteger> {

    Page<PostComment> pageByPost(Post post, Pageable pageable);

    Page<PostComment> pageByPostId(BigInteger id,Pageable pageable);

    Optional<PostComment> insert(PostCommentInsert postCommentInsert);

    PostCommentVO pageByPostIdWithVO(BigInteger id,Pageable pageable);

}
