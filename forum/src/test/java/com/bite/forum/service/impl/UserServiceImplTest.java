package com.bite.forum.service.impl;

import com.bite.forum.model.User;
import com.bite.forum.util.MD5Utils;
import com.bite.forum.util.UUIDUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

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



}