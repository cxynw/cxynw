package com.cxynw.service.impl;

import com.cxynw.event.logger.LogEvent;
import com.cxynw.manager.UserCacheDao;
import com.cxynw.model.does.User;
import com.cxynw.model.does.Permission;
import com.cxynw.model.does.Actor;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.model.param.AccountParam;
import com.cxynw.model.param.LogParam;
import com.cxynw.model.vo.UserItemVo;
import com.cxynw.repository.UserRepository;
import com.cxynw.service.AccountService;
import com.cxynw.service.base.AbstractCrudService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AccountServiceImpl extends AbstractCrudService<User, BigInteger> implements AccountService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserCacheDao userCacheDao;

    public AccountServiceImpl(UserRepository repository, ApplicationEventPublisher applicationEventPublisher, UserCacheDao userCacheDao) {
        super(repository);

        this.userRepository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userCacheDao = userCacheDao;
    }

    @Override
    public @NonNull Optional<User> getCurrentAccount() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal == null){
            return Optional.empty();
        }

        if(!(principal instanceof UserDetails)){
            return Optional.empty();
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        return findAccountByUsername(username);

    }

    @Override
    public @NonNull User createByUserParam(@NonNull AccountParam accountParam) {
        User user = new User();
        user.setNickname(accountParam.getNickname());
        user.setUsername(accountParam.getUsername());
        String password = accountParam.getPassword();
        String encryptionPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(encryptionPassword);
        return user;
    }

    /**
     *
     * @see AccountServiceImpl
     * @param username
     * @return
     */
    @Transactional
    @Override
    public UserDetails findUserDetailsByUsername(@NonNull String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        User user = optional.orElseThrow(
                () -> {
                    applicationEventPublisher.publishEvent(new LogEvent(this,
                            new LogParam(LogTypeEnum.USERNAME_NOT_FOUND,"username not found")));
                    return new UsernameNotFoundException("");
                });

        ArrayList<GrantedAuthority> permissions = new ArrayList<>();
        Set<Actor> actors = user.getActors();
        actors.forEach((role -> {
            permissions.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
            Set<Permission> permissionSet = role.getPermissions();
            permissionSet.forEach((permission -> {
                permissions.add(new SimpleGrantedAuthority(permission.getPermissionName()));
            }));
        }));

        if(log.isDebugEnabled()){
            log.debug("permissions: {}",permissions);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(permissions)
                .build();
    }

    @Override
    public Optional<User> findAccountByUsername(@NonNull String username) {
        return userCacheDao.findByUsername(username);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserItemVo findAll(PageRequest pageRequest) {
        Page<User> userPage = userRepository.findAll(pageRequest);
        return UserItemVo.generate(userPage);
    }
}
