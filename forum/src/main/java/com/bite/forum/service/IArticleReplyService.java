package com.bite.forum.service;

import com.bite.forum.model.ArticleReply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleReplyService {

    /*
    * 新增回复
     */
    @Transactional
    void create(ArticleReply articleReply);

    /**
     * 查询帖⼦对应的回复
     * @param articleId 帖⼦Id
     * @return
     */
    List<ArticleReply> selectByArticleId (Long articleId);

}
