package com.cxynw.service;

import com.cxynw.model.does.User;
import com.cxynw.model.param.AccountParam;
import com.cxynw.service.base.CrudService;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Optional;

public interface AccountService extends CrudService<User, BigInteger> {

    /**
     * Gets current account.
     *
     * @return an optional account
     */
    @NonNull
    Optional<User> getCurrentAccount();

    @NonNull
    User createByUserParam(@NonNull AccountParam accountParam);

    UserDetails findUserDetailsByUsername(@NonNull String username);

    Optional<User> findAccountByUsername(@NonNull String username);


}
