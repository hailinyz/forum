package com.bite.forum.service.impl;

import com.bite.forum.model.Message;
import com.bite.forum.service.IMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageServiceImplTest {

    @Resource
    private IMessageService messageService;

    @Resource
    private ObjectMapper objectMapper;

    @Transactional
    @Test
    void create() throws JsonProcessingException {
        Message message = new Message();
        message.setPostUserId(1L); // 发送者ID
        message.setReceiveUserId(19L); // 接收者ID
        message.setContent("测试"); // 内容
        messageService.create(message); // 创建消息
        System.out.println(objectMapper.writeValueAsString(message)); // 打印消息
    }
}
