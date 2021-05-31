package com.cxynw.manager;

import com.cxynw.dao.UserDao;
import com.cxynw.model.does.FileMark;
import com.cxynw.model.does.User;
import com.cxynw.repository.FileMarkRepository;
import com.cxynw.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Optional;

@Slf4j
@Repository
public class UserCacheDaoImpl implements UserCacheDao {

    private final UserDao userDao;
    private final UserRepository userRepository;
    private final FileMarkRepository fileMarkRepository;

    public UserCacheDaoImpl(UserDao userDao, UserRepository userRepository, FileMarkRepository fileMarkRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
        this.fileMarkRepository = fileMarkRepository;
    }

    @Override
    public Optional<User> findById(@NotNull BigInteger id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByUsername(@NotNull String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> updateUsernameById(String username, BigInteger id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            return Optional.empty();
        }
        User user = optional.get();
        user.setUsername(username);
        return optional;
    }

    @Override
    public Optional<User> updateNicknameById(String nickname, BigInteger id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            return Optional.empty();
        }
        User user = optional.get();
        user.setNickname(nickname);
        return optional;
    }

    @Transactional
    @Override
    public Optional<User> updateAvatarById(BigInteger avatarId, BigInteger userId) {
        if(log.isDebugEnabled()){
            log.debug("update avatar by user id");
        }

        Optional<User> optional = userRepository.findById(userId);
        Optional<FileMark> fileMark = fileMarkRepository.findById(avatarId);
        if(fileMark.isEmpty()){
            return Optional.empty();
        }
        if(optional.isEmpty()){
            return Optional.empty();
        }

        User user = optional.get();
        user.setAvatarId(avatarId);

        if(log.isDebugEnabled()){
            log.debug("return result: {}",optional);
        }

        return optional;
    }
}
