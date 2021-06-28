package com.cxynw.dao.base;

import com.cxynw.model.does.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BaseDaoImplTest {

    @Autowired
    BaseDaoImpl<User, BigInteger> baseDao;


    @Test
    @Transactional
    void findById() {
        Optional<User> optionalUser = baseDao.findById(BigInteger.ONE);
        log.debug("user: [{}]",optionalUser);
        assertFalse(optionalUser.isEmpty(),"没有查到测试环境必有的账户admin");
        User user = optionalUser.get();
        log.debug("userid: [{}] nickname: [{}] username: [{}]",user.getUserId(),user.getNickname(),user.getUsername());
        assertEquals(BigInteger.ONE,user.getUserId());
        assertEquals("管理员",user.getNickname(),"请将初始账户的昵称改回【管理员】");
        assertEquals("admin",user.getUsername(),"请将初始账户的用户名改回【admin】");
    }

    @Test
    @Transactional
    void findAll() {
        Page<User> userPage = baseDao.findAll(PageRequest.of(0, 12));
        log.debug("user page: [{}]",userPage);
        int size = userPage.getContent().size();
        List<User> content = userPage.getContent();
        for(int i=0;i<size;i++){
            BigInteger userid = content.get(i).getUserId();
            log.debug("expected: [{}] actual: [{}]",i+1,userid);
            assertTrue(userid.equals(BigInteger.valueOf(i+1)),"如果数据库数据改成了可以物理删除，请修改此处");
        }
    }

    @Test
    @Transactional
    void findByProperties() {
        User user = new User();
        user.setUsername("admin");
        List<User> users = baseDao.findByProperties(user);
        log.debug("users: [{}]",users);
        assertEquals(1,users.size(),"根据用户名查询初始账户");
        assertEquals("管理员",users.get(0).getNickname(),"请将初始账户的昵称改回【管理员】");
        assertEquals("admin",users.get(0).getUsername(),"请将初始账户的用户名改回【admin】");
    }

    @Test
    @Transactional
    void deleteById() {
        boolean delete = baseDao.deleteById(BigInteger.ONE);
        assertTrue(delete,"删除初始账户失败");
        Optional<User> optionalUser = baseDao.findById(BigInteger.ONE);
        assertTrue(optionalUser.isEmpty(),"删除初始化账户失败");
    }

    @Test
    @Transactional
    void insert() {
        User user = new User();
        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String email = UUID.randomUUID().toString();
        String nickname = UUID.randomUUID().toString();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setNickname(nickname);
        Optional<User> optionalUser = baseDao.insert(user);
        log.debug("optional user: [{}]",optionalUser);
        assertTrue(optionalUser.isPresent(),"插入失败");
        User u = optionalUser.get();
        assertEquals(username,u.getUsername(),"用户名校验失败");
        assertEquals(password,u.getPassword(),"密码校验失败");
        assertEquals(email,u.getEmail(),"邮箱校验失败");
        assertEquals(nickname,u.getNickname(),"昵称校验失败");
    }

    /**
     * 这里不能加事务，如果加了事务会导致在set时就改变了值，导致无法严重update方法的正确性
     */
    @Test
    void update() {
        Optional<User> optionalUser = baseDao.findById(BigInteger.ONE);
        assertEquals(true,optionalUser.isPresent(),"请插入初始的测试账号admin");
        User user = optionalUser.get();
        String newUsername = UUID.randomUUID().toString();
        user.setUsername(newUsername);
        baseDao.update(user);
        Optional<User> optional = baseDao.findById(BigInteger.ONE);
        assertEquals(newUsername,optional.get().getUsername(),"修改用户名失败");
        optional.get().setUsername("admin");
        Optional<User> adminUser = baseDao.update(optional.get());
        assertEquals("admin",adminUser.get().getUsername(),"修改用户名失败");
    }

    @Test
    void getId() {
        User user = new User();
        user.setUserId(BigInteger.ZERO);
        BigInteger id = baseDao.getId(user);
        assertEquals(id,BigInteger.ZERO,"获取实体ID信息失败");
    }

}