package com.bite.forum.service.impl;

import com.bite.forum.model.ArticleReply;
import com.bite.forum.service.IArticleReplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class ArticleReplyServiceImplTest {

    @Resource
    private IArticleReplyService articleReplyService;
    @Resource
    private ObjectMapper objectMapper;

    @Transactional
    @Test
    void create_Success() throws JsonProcessingException {
        ArticleReply articleReply = new ArticleReply();
        articleReply.setArticleId(15L);
        articleReply.setPostUserId(1L);
        articleReply.setReplyId(1L);
        articleReply.setReplyUserId(1L);
        articleReply.setContent("测试6");
        articleReplyService.create(articleReply);
        System.out.println(objectMapper.writeValueAsString(articleReply));
    }
}