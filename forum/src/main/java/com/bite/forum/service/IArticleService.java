package com.bite.forum.service;

import com.bite.forum.model.Article;
import org.springframework.transaction.annotation.Transactional;

public interface IArticleService {

    /*
    发布帖子
     */
    @Transactional
    void create(Article article);

}
