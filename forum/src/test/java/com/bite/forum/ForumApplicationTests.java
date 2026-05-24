package com.bite.forum;

import com.bite.forum.dao.UserMapper;
import com.bite.forum.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
class ForumApplicationTests {

    //数据源
    @Resource
    private DataSource dataSource;

    @Resource
    private UserMapper userMapper;

    @Test
    void testUserMapper() {
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user.getUsername());
    }


    @Test
    void testDataSource() throws Exception {
        System.out.println("dataSource = " + dataSource.getClass());
        // 获取数据库连接
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
        connection.close();
    }


    @Test
    void contextLoads() {
        System.out.println("TEST: 基于Spring的论坛系统-前后端分离");
    }

}