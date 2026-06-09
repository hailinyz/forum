package com.bite.forum.service.impl;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.dao.ArticleMapper;
import com.bite.forum.dao.ArticleReplyMapper;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.Article;
import com.bite.forum.model.ArticleReply;
import com.bite.forum.service.IArticleReplyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ArticleReplyServiceImpl implements IArticleReplyService {


    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleReplyMapper articleReplyMapper;

    /*
    * 新增回复
     */
    @Override
    public void create(ArticleReply articleReply) {
        //非空校验
        if (articleReply == null || articleReply.getArticleId() == null){
            log.info(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //查询帖子信息
        Article article = articleMapper.selectByPrimaryKey(articleReply.getArticleId());
        //校验帖子信息
        if (article == null) {
            log.info(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }
        //判断帖子状态
        if (article.getDeleteState() != 0 || article.getState() != 0) {
            log.info(ResultCode.FAILED_ARTICLE_BANNED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED));
        }

        //填充默认数据
        articleReply.setState((byte) 0);
        articleReply.setDeleteState((byte) 0);
        articleReply.setLikeCount(0);
        Date date = new Date();
        articleReply.setCreateTime(date);
        articleReply.setUpdateTime(date);
        //写入回复数据
        int row = articleReplyMapper.insertSelective(articleReply);
        if (row != 1) {
            //打印日志
            log.info("新增帖⼦回复失败, userId = " + articleReply.getPostUserId() + ", articleId = " + articleReply.getArticleId());
            throw new ApplicationException((AppResult.failed(ResultCode.FAILED_CREATE)));
        }

        //更新帖子回复数
        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setReplyCount(article.getReplyCount() + 1);
        int updateRow = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (updateRow != 1) {
            //打印日志
            log.info("帖⼦回复数量更新失败, userId = " + articleReply.getPostUserId() + ", articleId = " + articleReply.getArticleId());
            throw new ApplicationException((AppResult.failed(ResultCode.FAILED_CREATE)));
        }
    }






}
