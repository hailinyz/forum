package com.bite.forum.service;

import com.bite.forum.model.ArticleReply;
import org.springframework.transaction.annotation.Transactional;

public interface IArticleReplyService {

    /*
    * 新增回复
     */
    @Transactional
    void create(ArticleReply articleReply);
}
