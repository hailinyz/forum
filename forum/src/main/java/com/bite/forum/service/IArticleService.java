package com.bite.forum.service;

import com.bite.forum.model.Article;
import com.bite.forum.model.ArticleReply;
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

    /*
    根据id查询指定帖子详情
     */
    Article selectDetailById(Long id);

    /*
    根据帖⼦Id更新帖⼦标题与内容
     */
    void modify(Long id, String title, String content);

    Article selectById(Long id);

    /*
    点赞
     */
    void thumbsUpById(Long id);

    /*
    根据Id删除帖⼦
     */
    @Transactional // 事务管理
    void deleteById (Long id);

    /*
    新增回复
     */
    @Transactional
    void create(ArticleReply articleReply);






}
