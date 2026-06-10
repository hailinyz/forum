package com.bite.forum.controller;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.config.AppConfig;
import com.bite.forum.model.Message;
import com.bite.forum.model.User;
import com.bite.forum.service.IMessageService;
import com.bite.forum.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/message")
@Tag(name = "站内信接口")
public class MessageController {

    @Resource
    private IMessageService messageService;

    @Resource
    private IUserService userService;


    @Operation(summary = "发送站内信")
    @PostMapping("/send")
    public AppResult send(HttpServletRequest  request, Long receiveUserId, String  content){
        //获取发送用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //判断是否被禁言
        if (user.getState() != 0){
            //打印日志
            log.info(ResultCode.FAILED_USER_BANNED.toString());
            //返回错误信息(一般不在Controller抛异常，只在Service抛异常)
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //不能自己给自己发站内信
        if (user.getId() == receiveUserId){
            //打印日志
            log.warn("不能给⾃⼰发送站内信. postUserId = " + user.getId() + ",  receiveUserId = " + receiveUserId);
            return AppResult.failed(ResultCode.FAILED_CREATE);
        }
        //获取接收用户信息
        User receiveUser = userService.selectById(receiveUserId);
        //判断接收用户是否存在
        if (receiveUser == null || receiveUser.getDeleteState() != 0){
            //打印日志
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.toString());
            return AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        //构建站内信
        Message message = new Message();
        message.setPostUserId(user.getId());
        message.setReceiveUserId(receiveUserId);
        message.setContent(content);
        messageService.create(message);
        return AppResult.success();
    }


    @GetMapping("/getUnreadCount")
    @Operation(summary = "获取未读消息数量")
    public AppResult<Integer> getUnreadCount(HttpServletRequest request){
        //获取当前用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //获取未读消息数量
        Integer result = messageService.selectUnreadCount(user.getId());
        return AppResult.success(result);
    }











}
