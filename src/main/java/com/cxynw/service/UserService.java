package com.cxynw.service;


import com.cxynw.model.does.User;
import com.cxynw.model.param.AccountParam;
import com.cxynw.model.query.RestNickname;
import com.cxynw.model.query.RestPassword;
import com.cxynw.model.response.BaseSuccessResponse;

import java.math.BigInteger;
import java.util.Optional;

public interface UserService {

    /**
     * 创建一个新的用户，并为其分配对应的角色
     *
     * @param accountParam
     * @return
     */
    BaseSuccessResponse createNewUser(AccountParam accountParam);

    BaseSuccessResponse restPassword(RestPassword restPassword);

    BaseSuccessResponse restNickname(RestNickname restNickname);

    Optional<User> findByEmail(String email);

    boolean setAvatarById(BigInteger avatarId, BigInteger userId);

}
