package com.cxynw.repository;

import com.cxynw.model.does.PostGroup;
import com.cxynw.model.does.User;
import com.cxynw.model.does.Post;
import com.cxynw.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

public interface PostRepository extends BaseRepository<Post, BigInteger> {

    Page<Post> findByPublisher(User publisher, Pageable pageable);

    Page<Post> findByPostGroups(PostGroup group,Pageable pageable);

    @Modifying
    @Query("update Post set visits = visits+1 where postId = :postId")
    int addVisitsById(@Param("postId") @NotNull BigInteger id);

}
