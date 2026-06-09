package com.bite.forum.service.impl;

import com.bite.forum.model.Article;
import com.bite.forum.model.ArticleReply;
import com.bite.forum.service.IArticleReplyService;
import com.bite.forum.service.IArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
class ArticleServiceImplTest {

    @Resource
    private IArticleReplyService articleReplyService;

    @Resource
    private IArticleService articleService;

    @Resource
    private ObjectMapper objectMapper;


    @Transactional
    @Test
    void create() {
        Article article = new Article();
        article.setUserId(1L);
        article.setBoardId(1L);
        article.setTitle("测试");
        article.setContent("测试");
        articleService.create(article);
        System.out.println(article.getId());
    }

    @Test
    void selectAll() throws JsonProcessingException {
        List<Article> articles = articleService.selectAll();
        System.out.println(objectMapper.writeValueAsString(articles));
    }


    @Test
    void selectByBoardId() throws JsonProcessingException {
        List<Article> articles = articleService.selectByBoardId(1L);
        //用json的方法打印
        System.out.println(objectMapper.writeValueAsString(articles));
    }

    @Transactional
    @Test
    void selectDetailById() throws JsonProcessingException {
        Article article = articleService.selectDetailById(7L);
        System.out.println(objectMapper.writeValueAsString(article));
    }

    @Transactional
    @Test
    void modify() {
        articleService.modify(7L, "测试修改", "测试修改");
        System.out.println("修改成功");
    }

    @Test
    void selectById() throws JsonProcessingException {
        Article article = articleService.selectById(7L);
        System.out.println(objectMapper.writeValueAsString(article));
    }

    @Transactional
    @Test
    void thumbsUpById() {
        articleService.thumbsUpById(7L);
        System.out.println("点赞成功");
    }

    @Transactional
    @Test
    void deleteById() throws JsonProcessingException {
        System.out.println("删除前");
        System.out.println(objectMapper.writeValueAsString(articleService.selectById(7L)));
        System.out.println("删除后");
        articleService.deleteById(7L);
        System.out.println(objectMapper.writeValueAsString(articleService.selectById(7L)));
    }

//    @Transactional
    @Test
    void testCreate() throws JsonProcessingException {
        ArticleReply articleReply = new ArticleReply();
        articleReply.setArticleId(15L);
        articleReply.setPostUserId(1L);
        articleReply.setReplyUserId(1L);
        articleReply.setReplyId(1L);
        articleReply.setContent("这是一条测试回复");

        articleService.create(articleReply);

        System.out.println("回复创建成功，ID: " + articleReply.getId());

        Article article = articleService.selectById(15L);
        System.out.println("帖子回复数: " + article.getReplyCount());
        System.out.println(objectMapper.writeValueAsString(articleReply));
    }

    @Test
    void selectByArticleId() throws JsonProcessingException {
        List<ArticleReply> articleReplies = articleReplyService.selectByArticleId(15L);
        System.out.println(objectMapper.writeValueAsString(articleReplies));
    }
}

