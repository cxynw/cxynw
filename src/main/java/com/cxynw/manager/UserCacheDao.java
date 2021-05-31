package com.cxynw.manager;

import com.cxynw.model.does.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Optional;

/**
 * 带缓存的UserDao服务
 */
public interface UserCacheDao {

    @Cacheable(value = "User::Id",
            key = "#result.userId",
            unless = "#result == null")
    Optional<User> findById(@NotNull BigInteger id);

    @Cacheable(value = "User::Username",
            key = "#root.args[0]",
            unless = "#result == null")
    Optional<User> findByUsername(@NotNull String username);

    @Caching(put = {
            @CachePut(value = "User::Id",key = "#result.userId",unless = "#result == null"),
            @CachePut(value = "User::Username",key = "#result.username",unless = "#result==null")
    })
    Optional<User> updateUsernameById(String username,BigInteger id);

    @Caching(put = {
            @CachePut(value = "User::Id",key = "#result.userId",unless = "#result == null"),
            @CachePut(value = "User::Username",key = "#result.username",unless = "#result==null")
    })
    Optional<User> updateNicknameById(String nickname,BigInteger id);

    @Caching(put = {
            @CachePut(value = "User::Id",key = "#result.userId",unless = "#result == null"),
            @CachePut(value = "User::Username",key = "#result.username",unless = "#result==null")
    })
    Optional<User> updateAvatarById(BigInteger avatarId,BigInteger id);


}
