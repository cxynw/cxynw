package com.cxynw.repository;

import com.cxynw.model.does.User;
import com.cxynw.repository.base.BaseRepository;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, BigInteger> {

    Optional<User> findByUsername(@NotNull String username);

    Optional<User> findByEmail(@NotNull String email);

    @Modifying
    @Query("update User set avatarId = :avatarId where userId= :userId")
    int updateAvatarIdById(@Param("avatarId") BigInteger avatarId, @Param("userId")BigInteger userId);

}
