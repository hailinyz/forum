package com.bite.forum.controller;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.model.User;
import com.bite.forum.service.IUserService;
import com.bite.forum.util.MD5Utils;
import com.bite.forum.util.StringUtils;
import com.bite.forum.util.UUIDUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 添加⽇志注解以便使⽤⽇志
@Slf4j
// API 描述
@Tag(name = "用户相关接口类")
// 标识为了个Controller并且响应时返回Body中的数据
@RestController
// 指定路径映射
@RequestMapping("/user")
public class UserController {
    // 注⼊业务层
    @Resource
    private IUserService userService;

    // 指定接⼝URL映射
    @PostMapping("/register")
    public AppResult register(String username, String nickname, String
            password, String passwordRepeat) {
        // 校验参数的有效性
        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(passwordRepeat)
                || StringUtils.isEmpty(nickname)) {
            // 记录⽇志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            // 返回错误信息
            return AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE);
        }
        // 1.基本信息赋值
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        // 2.处理密码
        // 2.1 校验密码和确认密码是否相等
        if (!password.equals(passwordRepeat)) {
            // 记录⽇志
            log.info(ResultCode.FAILED_TWO_PWD_NOT_SAME.toString());
            // 返回错误信息
            return AppResult.failed(ResultCode.FAILED_TWO_PWD_NOT_SAME);
        }
        // 2.2 加密密码
        String salt = UUIDUtils.UUID_32();
        String encryptPassword = MD5Utils.md5Salt(password, salt);
        // 2.3 设置密码
        user.setPassword(encryptPassword);
        user.setSalt(salt);
        // 3. 调⽤Service
        userService.createNormalUser(user);
        // 4. 返回
        return AppResult.success();
    }
}
