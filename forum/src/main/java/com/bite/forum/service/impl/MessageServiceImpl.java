package com.bite.forum.service.impl;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.dao.MessageMapper;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.Message;
import com.bite.forum.model.User;
import com.bite.forum.service.IMessageService;
import com.bite.forum.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl implements IMessageService {

    @Resource
    private IUserService userService;

    @Resource
    private MessageMapper messageMapper;

    /*
    发送站内信息
     */
    @Transactional
    @Override
    public void create(Message message) {
        //非空校验
        if (message == null || message.getPostUserId() == null || message.getReceiveUserId() == null){
            //打印日志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //校验接收者是否存在
        User receiveUser = userService.selectById(message.getReceiveUserId());
        if (receiveUser == null || receiveUser.getDeleteState() == 1){
            //打印日志
            log.info(ResultCode.FAILED_USER_NOT_EXISTS.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        //设置默认值
        message.setState((byte) 0); // 0表示未读状态
        message.setDeleteState((byte) 0);
        Date date = new Date();
        message.setCreateTime(date);
        message.setUpdateTime(date);
        //插入数据库
        int row = messageMapper.insertSelective(message);
        if (row != 1) {
            //打印日志
            log.info(ResultCode.FAILED_CREATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
    }



    /*
    查询未读消息数量
     */
    @Override
    public Integer selectUnreadCount(Long userId) {
        //非空校验
        if (userId == null){
            //打印日志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //查询未读消息数量
        Integer count = messageMapper.selectUnreadCount(userId);
        return count;
    }

    @Override
    public List<Message> selectByReceiveUserId(Long receiveUserId) {
        //非空校验
        if (receiveUserId == null){
            //打印日志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        List<Message> result = messageMapper.selectByReceiveUserId(receiveUserId);
        return result;
    }

    /*
    根据id查询站内信
     */
    @Override
    public Message selectById(Long id) {
        //非空校验
        if (id == null){
            //打印日志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        Message result = messageMapper.selectByPrimaryKey(id);
        return result;
    }

    /*
    更新站内信状态
     */
    @Override
    public void updateStateById(Long id, Byte state) {
        //非空校验
        if (id == null || state == null){
            //打印日志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //构建更新对象
        Message message = new Message();
        message.setId(id);
        message.setState(state);
        int row = messageMapper.updateByPrimaryKeySelective(message);
        if (row != 1) {
            //打印日志
            log.info(ResultCode.ERROR_SERVICES.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }

    /*
    回复站内信
     */
    @Override
    public void reply(Long repliedId, Message message) {
        //非空校验
        if (repliedId == null || repliedId <= 0){
            //打印日志
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //校验repliedId对应的站内信是否存在
        Message existsMessage = messageMapper.selectByPrimaryKey(repliedId);
        if (existsMessage == null){
            //打印日志
            log.info(ResultCode.FAILED_MESSAGE_NOT_EXISTS.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_MESSAGE_NOT_EXISTS));
        }
        //更新状态为已回复
        updateStateById(repliedId, (byte)2);
        //插入回复
        create(message);
    }









}
