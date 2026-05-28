package com.bite.forum.service;


import com.bite.forum.model.User;
import org.springframework.stereotype.Service;

/*
* 用户服务接口
 */
public interface IUserService {

    /*
    * 创建普通用户
     */
    void createNormalUser(User user);


}
