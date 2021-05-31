package com.cxynw.dao.impl;

import com.cxynw.dao.UserDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.User;
import com.cxynw.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Optional;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User,BigInteger> implements UserDao {

    private final UserRepository repository;

    public UserDaoImpl(@NotNull UserRepository baseRepository) {
        super(baseRepository);
        this.repository = baseRepository;
    }

    @Override
    protected BigInteger getId(User user) {
        return user.getUserId();
    }

    @Override
    public Optional<User> findByEmail(@NotNull String email) {
        return repository.findByEmail(email);
    }

}
