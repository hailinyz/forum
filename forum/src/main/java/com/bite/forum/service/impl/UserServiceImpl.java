package com.bite.forum.service.impl;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.dao.UserMapper;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.User;
import com.bite.forum.service.IUserService;
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










}
