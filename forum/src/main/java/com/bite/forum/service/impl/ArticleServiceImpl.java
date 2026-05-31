package com.bite.forum.service.impl;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.dao.ArticleMapper;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.Article;
import com.bite.forum.model.Board;
import com.bite.forum.model.User;
import com.bite.forum.service.IArticleService;
import com.bite.forum.service.IBoradService;
import com.bite.forum.service.IUserService;
import com.bite.forum.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private IUserService userService; ;

    @Resource
    private IBoradService boradService;


    /*
    * 发布帖子
     */
    @Override
    public void create(Article article) {
        //非空校验
        if (article == null || article.getUserId() ==  null || article.getBoardId() == null
            || StringUtils.isEmpty(article.getTitle())
            || StringUtils.isEmpty(article.getContent())){
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //设置默认值
        article.setVisitCount(0); //访问数
        article.setReplyCount(0); //回复数
        article.setLikeCount(0); //点赞数
        article.setState((byte) 0); //状态
        article.setDeleteState((byte) 0); //删除状态
        Date date = new Date();
        article.setCreateTime(date); //创建时间
        article.setUpdateTime(date); //更新时间
        int ArticleRow = articleMapper.insertSelective(article);
        if (ArticleRow <= 0) {
            //打印日志
            log.warn(ResultCode.FAILED_CREATE.toString());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        //获取用户信息
        User user = userService.selectById(article.getUserId());
        //没有找到指定的用户信息
        if (user == null) {
            //打印日志
            log.warn(ResultCode.FAILED_CREATE.toString() + "user id = " + article.getUserId());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        //更新用户发帖数
        userService.addOneArticleCountById(user.getId());

        //获取板块信息
        Board board = boradService.selectById(article.getBoardId());
        //是否找到指定的板块信息
        if (board == null) {
            //打印日志
            log.warn(ResultCode.FAILED_CREATE.toString() + "board id = " + article.getBoardId());
            //抛出异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        //更新板块中的帖子数量
        boradService.addOneArticleCountById(board.getId());
        //打印日志
        log.info(ResultCode.SUCCESS.toString());
    }

    /*
    * 查询所有帖子
     */
    @Override
    public List<Article> selectAll() {
        //查询所有帖子
        List<Article> articles = articleMapper.selectAll();
        return articles;
    }

    /*
    * 根据板块id查询帖子
     */
    @Override
    public List<Article> selectByBoardId(Long boardId) {
        //非空校验
        if (boardId == null || boardId <= 0) {
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //校验板块是否存在
        Board board = boradService.selectById(boardId);
        if (board == null) {
            //打印日志
            log.warn(ResultCode.FAILED_BOARD_NOT_EXISTS.toString() + "board id = " + boardId);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_NOT_EXISTS));
        }
        //调用DAO、查询
        List<Article> articles = articleMapper.selectAllByBoardId(boardId);
        return articles;
    }

    /*
    * 根据帖子id查询帖子详情
     */
    @Override
    public Article selectDetailById(Long id) {
        //非空校验
        if (id == null || id <= 0) {
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //调用DAO
        Article article = articleMapper.selectDetailById(id);
        //判断结果是否为空
        if (article == null) {
            //打印日志
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString() + "article id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }
        //更新帖子访问数
        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setVisitCount(article.getVisitCount() + 1);
        int updateRow = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (updateRow != 1) {
            //打印日志
            log.warn(ResultCode.ERROR_SERVICES.toString() + "article id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
        //返回帖子详情 - 为返回对象进行数据更新（访问次数更新）
        article.setVisitCount(article.getVisitCount() + 1);
        return article;
    }








}
