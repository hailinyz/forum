package com.bite.forum.service;

import com.bite.forum.model.Message;

import java.util.List;

public interface IMessageService {

    /**
     * 发送站内信息
     * @param message 站内信
     */
    void create (Message message);

    /*
    * 查询未读信息数量
     */
    Integer selectUnreadCount (Long userId);


    /*
    根据接收这ids查询所有站内信
     */
    List<Message> selectByReceiveUserId (Long receiveUserId);
}
