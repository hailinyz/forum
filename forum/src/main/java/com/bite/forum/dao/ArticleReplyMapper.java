package com.bite.forum.dao;

import com.bite.forum.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleReplyMapper {
    int insert(ArticleReply row);

    int insertSelective(ArticleReply row);

    ArticleReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleReply row);

    int updateByPrimaryKey(ArticleReply row);


    /**
     * 查询帖⼦对应的回复
     * @param articleId 帖⼦Id
     * @return
     */
    List<ArticleReply> selectByArticleId (Long articleId);

}