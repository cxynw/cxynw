package com.cxynw.dao;

import com.cxynw.model.does.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserDaoTest {

    @Autowired
    UserDao userDao;

    // 查询的邮箱不存在
    @Test
    @Transactional
    void findByEmail01() {
        String email = "null@null.com";
        Optional<User> user = userDao.findByEmail(email);
        log.info("email: [{}] user: [{}]",email,user);
        assertFalse(user.isPresent());
    }

    // 查询的邮箱存在
    @Test
    @Transactional
    void findByEmail02() {
        String email = "test@email.com";
        Optional<User> user = userDao.findByEmail(email);
        log.info("email: [{}] user: [{}]",email,user);
        assertTrue(user.isPresent());
    }

}