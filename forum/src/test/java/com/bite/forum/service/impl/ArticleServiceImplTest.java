package com.bite.forum.service.impl;

import com.bite.forum.model.Article;
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
        System.out.println(objectMapper.writeValueAsString( article));
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



}