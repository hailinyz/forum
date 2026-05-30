package com.bite.forum.service;

import com.bite.forum.model.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleService {

    /*
    发布帖子
     */
    @Transactional
    void create(Article article);

    /*
    查询所有帖子
     */
    List<Article> selectAll();

    /*
    查询指定板块帖子
     */
    List<Article> selectByBoardId(Long boardId);



}
