package com.bite.forum.service;

import com.bite.forum.model.Message;

public interface IMessageService {

    /**
     * 发送站内信息
     * @param message 站内信
     */
    void create (Message message);

}
