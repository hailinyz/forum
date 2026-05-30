package com.bite.forum.service.impl;

import com.bite.forum.model.User;
import com.bite.forum.util.MD5Utils;
import com.bite.forum.util.UUIDUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Transactional // 开启事务
    @Test
    void createNormalUser() {
        //构建User对象
        User user = new User();
        user.setUsername("test");
        user.setNickname("测试用户");

        //定义一个原始密码
        String originalPassword = "123456";
        //生成盐
        String salt = UUIDUtils.UUID_32();
        //生成密码的密文
        String ciphertext = MD5Utils.md5Salt(originalPassword, salt);
        //设置加密后的密码
        user.setPassword(ciphertext);
        //设置盐
        user.setSalt(salt);

        userService.createNormalUser(user);
        //打印结果
        System.out.println(user);
    }


    @Test
    void selectByUserName() {
        User user = userService.selectByUserName("test");
        System.out.println(user);
    }

    @Test
    void login() {
        User user = userService.login("test", "123456");
        System.out.println(user);
    }

    @Test
    void selectById() {
        User user = userService.selectById(1L);
        System.out.println(user);
    }

    @Transactional
    @Test
    void addOneArticleCountById() {
        userService.addOneArticleCountById(1L);
        System.out.println(userService.selectById(1L));
    }
}