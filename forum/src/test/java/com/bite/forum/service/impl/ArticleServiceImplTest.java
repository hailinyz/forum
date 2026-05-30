package com.bite.forum.service.impl;

import com.bite.forum.model.Article;
import com.bite.forum.service.IArticleService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceImplTest {

    @Resource
    private IArticleService articleService;


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
}