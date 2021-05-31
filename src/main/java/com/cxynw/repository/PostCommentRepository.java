package com.cxynw.repository;

import com.cxynw.model.does.Post;
import com.cxynw.model.does.PostComment;
import com.cxynw.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public interface PostCommentRepository extends BaseRepository<PostComment, BigInteger> {

    Page<PostComment> findAllByPost(Post post, Pageable pageable);

}
