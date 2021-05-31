package com.cxynw.service.impl;

import com.cxynw.dao.UserDao;
import com.cxynw.exception.api.BaseApiRuntimeException;
import com.cxynw.manager.UserCacheDao;
import com.cxynw.model.does.User;
import com.cxynw.model.does.Actor;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.model.enums.RoleEnum;
import com.cxynw.model.param.AccountParam;
import com.cxynw.model.query.RestNickname;
import com.cxynw.model.query.RestPassword;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.service.AccountService;
import com.cxynw.service.UserService;
import com.cxynw.utils.EntityUtils;
import com.cxynw.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final AccountService accountService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final LogUtils logUtils;
    private final UserDao userDao;
    private final UserCacheDao userCacheDao;

    public UserServiceImpl(AccountService accountService,
                           ApplicationEventPublisher applicationEventPublisher,
                           LogUtils logUtils,
                           UserDao userDao, UserCacheDao userCacheDao) {
        this.accountService = accountService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.logUtils = logUtils;
        this.userDao = userDao;
        this.userCacheDao = userCacheDao;
    }


    @Transactional
    @Override
    public BaseSuccessResponse<Object> createNewUser(AccountParam accountParam) {
        Optional<User> optional = userCacheDao.findByUsername(accountParam.getUsername());
        if(optional.isPresent()){
            throw new BaseApiRuntimeException(CodeEnum.SIGN_IN_FAIL, "该用户名已经被占用");
        }


        User user = accountParam.convertTo();
        Set<Actor> actorSet = EntityUtils.convertToSetRoles(RoleEnum.USER.getActor());
        user.setActors(actorSet);
        accountService.create(user);

        logUtils.publishEvent(this,LogTypeEnum.USER_REGISTER,accountParam.getUsername()+"成为了您的用户");

        return new BaseSuccessResponse<>(CodeEnum.SIGN_IN_SUCCEED.value(), "注册成功");
    }

    @Override
    @Transactional
    public BaseSuccessResponse restPassword(RestPassword restPassword) {
        Optional<User> account = accountService.getCurrentAccount();
        if(account.isEmpty()){
            throw new BaseApiRuntimeException(CodeEnum.ERROR,"你还没有登录");
        }
        User user = userDao.findById(account.get().getUserId()).get();
        user.setPassword(BCrypt.hashpw(restPassword.getPassword(),BCrypt.gensalt()));
        return new BaseSuccessResponse(CodeEnum.SUCCEED,"修改密码成功");
    }

    @Override
    @Transactional
    public BaseSuccessResponse restNickname(RestNickname restNickname) {
        Optional<User> account = accountService.getCurrentAccount();
        if(account.isEmpty()){
            throw new BaseApiRuntimeException(CodeEnum.ERROR,"你还没有登录");
        }
        userCacheDao.updateNicknameById(restNickname.getNickname(),account.get().getUserId());
        return new BaseSuccessResponse(CodeEnum.SUCCEED,"修改昵称成功");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(email == null){
            return Optional.empty();
        }
        return userDao.findByEmail(email);
    }

    @Override
    public boolean setAvatarById(BigInteger avatarId, BigInteger userId){
        if(log.isDebugEnabled()){
            log.debug("avatar id: {} user id: {}",avatarId,userId);
        }
        return userCacheDao.updateAvatarById(avatarId,userId) != null;
    }


}
