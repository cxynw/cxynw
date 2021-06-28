package com.cxynw.dao;

import com.cxynw.dao.base.BaseDao;
import com.cxynw.model.does.User;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Optional;

public interface UserDao extends BaseDao<User, BigInteger> {

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    Optional<User> findByEmail(@NotNull String email);

}
