package com.cxynw.manager;

import com.cxynw.model.does.PostAttachment;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface PostAttachmentCacheDao {

    @Cacheable(cacheNames = "PostAttachment::Id",key = "#root.args[0]",unless = "#result == null")
    Optional<PostAttachment> findById(BigInteger id);

    @Caching(evict = {
            @CacheEvict(cacheNames = "PostAttachment::Id",key = "#result.postAttachmentId"),
            @CacheEvict(cacheNames = "PostAttachment::PostId",key = "#result.postId")
    })
    Optional<PostAttachment> deleteById(BigInteger id);

    @Cacheable(cacheNames = "PostAttachment::PostId",key = "#root.args[0]",unless = "#result == null ")
    List<PostAttachment> findAllByPostId(@NotNull BigInteger postId);

    @Caching(
            put = @CachePut(cacheNames = "PostAttachment::Id",key = "#result.postAttachmentId",unless = "#result == null")
    )
    Optional<PostAttachment> insert(PostAttachment postAttachment);


}
