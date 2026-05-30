package com.bite.forum.service.impl;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.dao.UserMapper;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.User;
import com.bite.forum.service.IUserService;
import com.bite.forum.util.MD5Utils;
import com.bite.forum.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements IUserService { // 实现类 implements是继承

    @Resource //除了这个注解最新还可以用@Autowired
    private UserMapper userMapper;


    /*
    * 创建普通用户
     */
    @Override
    public void createNormalUser(User user) {
        //1.非空校验
        if (user == null || StringUtils.isEmpty(user.getUsername())
        || StringUtils.isEmpty(user.getNickname()) || StringUtils.isEmpty(user.getPassword())
        || StringUtils.isEmpty(user.getSalt())) {
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        //2.按用户名查询用户信息
        User existsUser = userMapper.selectByUserName(user.getUsername());
        //2.1判断用户是否存在
        if (existsUser != null) {
            //打印日志
            log.info(ResultCode.FAILED_USER_EXISTS.toString());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_EXISTS));
        }

        //3.新增用户流程 -- 设置默认值
        user.setGender((byte) 2);
        user.setArticleCount(0);
        user.setIsAdmin((byte) 0);
        user.setState((byte) 0);
        user.setDeleteState((byte) 0);
        // 当前日期
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        //4.写入数据库
        int row = userMapper.insertSelective(user);
        if (row != 1) {
            //打印日志
            log.info(ResultCode.FAILED_CREATE.toString());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        //打印日志
        log.info("新增用户成功. username =  " +  user.getUsername());

    }

    /*
    * 根据用户名查询用户信息
     */
    @Override
    public User selectByUserName(String username) {
        //非空校验
        if (StringUtils.isEmpty(username)) {
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //返回查询结果
        return userMapper.selectByUserName(username);
    }

    /*
    * 处理用户登录
     */
    @Override
    public User login(String username, String password) {
        //1. 非空校验
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            //打印日志
            log.warn(ResultCode.FAILED_LOGIN.toString());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }

        //2. 根据用户名查询用户信息
        User user = selectByUserName(username);

        //3. 对查询结果做非空校验
        if (user == null) {
            //打印日志
            log.warn(ResultCode.FAILED_LOGIN.toString() + "username = " + username);
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }

        //4. 处理密码 - 使用工具类的验证方法
        if (!MD5Utils.verifyOriginalAndCiphertext(password, user.getSalt(), user.getPassword())) {
            //打印日志
            log.warn(ResultCode.FAILED_LOGIN.toString() + "密码错误，username = " + username);
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }
        //打印登录成功的日志
        log.info("用户登录成功. username = " + username);
        //登录成功，返回用户信息
        return user;
    }

    /*
    * 根据id查询用户信息
     */
    @Override
    public User selectById(Long id) {
        //1. 非空校验
        if (id == null) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //2. 调用DAO层查询数据库并获取对象
        User user = userMapper.selectByPrimaryKey(id);
        //3. 返回结果
        return user;
    }

    /*
    * 更新当前用户的发帖数
     */
    @Override
    public void addOneArticleCountById(Long id) {
        //非空校验
        if (id == null || id <= 0) {
            //打印日志
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }
        //查询用户信息
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            //打印日志
            log.warn(ResultCode.ERROR_IS_NULL.toString() + "user id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //更新用户帖子数量
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setArticleCount(user.getArticleCount() + 1);
        int row = userMapper.updateByPrimaryKeySelective(updateUser);
        if (row != 1) {
            //打印日志
            log.warn(ResultCode.FAILED_USER_ARTICLE_COUNT.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_ARTICLE_COUNT));
        }
    }








}
