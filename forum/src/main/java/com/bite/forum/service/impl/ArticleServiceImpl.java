package com.bite.forum.service.impl;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.dao.ArticleMapper;
import com.bite.forum.dao.ArticleReplyMapper;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.Article;
import com.bite.forum.model.ArticleReply;
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
    private ArticleReplyMapper articleReplyMapper;

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

    /*
    根据帖⼦Id更新帖⼦标题与内容
     */
    @Override
    public void modify(Long id, String title, String content) {
        //非空校验
        if (id == null || id <= 0 || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)){
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //构建需要更新的帖子对象
        Article article = new Article();
        article.setId(id); //帖子id
        article.setTitle(title); //标题
        article.setContent(content); // 正文
        article.setUpdateTime(new Date()); //更新时间
        int updateRow = articleMapper.updateByPrimaryKeySelective(article);
        if (updateRow != 1) {
            //打印日志
            log.warn(ResultCode.ERROR_SERVICES.toString() + "article id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }

    /*
    根据帖子id查询记录
     */
    @Override
    public Article selectById(Long id) {
        if (id == null || id <= 0) {
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        Article article = articleMapper.selectByPrimaryKey(id);

        return article;
    }

    /*
    根据帖子id点赞
     */
    @Override
    public void thumbsUpById(Long id) {
        //非空校验
        if (id == null || id <= 0) {
            //打印日志
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //查询帖子信息
        Article article = selectById( id);
        if (article == null) {
            //打印日志
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString() + "article id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }
        //构建需要更新的帖子对象
        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setLikeCount(article.getLikeCount() + 1);
        int updateRow = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (updateRow != 1) {
            //打印日志
            log.warn(ResultCode.ERROR_SERVICES.toString() + "article id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
    }


    /*
    根据Id删除帖⼦
     */
    @Override
    public void deleteById(Long id) {
        //非空校验
        if (id == null || id <= 0) {
            //打印日志
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //根据id查询帖子信息
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null || article.getDeleteState() == 1) {
            //打印日志
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString() + "article id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }
        //构建需要更新的帖子对象
        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setDeleteState((byte) 1);
        int updateRow = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (updateRow != 1) {
            //打印日志
            log.warn(ResultCode.ERROR_SERVICES.toString() + "article id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }
        //更新板块帖子数(版块中的帖⼦数量 -1)
        boradService.subOneArticleCountById(article.getBoardId());
        //更新用户帖子数(用户中的帖⼦数量 -1)
        userService.subOneArticleCountById(article.getUserId());
        //打印日志
        log.info(ResultCode.SUCCESS.toString() + "被删除的article id = " + id);
    }


    /*
    新增回复
     */
    @Override
    public void create(ArticleReply articleReply) {
        //非空校验
        if (articleReply == null || articleReply.getArticleId() == null){
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //查询帖子信息
        Article article = articleMapper.selectByPrimaryKey(articleReply.getArticleId());
        if (article == null) {
            //打印日志
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString() + "article id = " + articleReply.getArticleId());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS));
        }
        //对帖子进行校验
        if (article.getDeleteState() != 0 || article.getState() != 0) { //帖子被删除或者被禁言
            //打印日志
            log.warn(ResultCode.FAILED_ARTICLE_BANNED.toString() + "article id = " + articleReply.getArticleId());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED));
        }
        //填充默认数据
        articleReply.setState((byte) 0); // 状态
        articleReply.setDeleteState((byte) 0); // 是否状态
        articleReply.setLikeCount(0); // 点赞数量
        // 时间
        Date date = new Date();
        articleReply.setCreateTime(date); // 创建时间
        articleReply.setUpdateTime(date); // 更新时间
        //写入回复数据
        int row = articleReplyMapper.insertSelective(articleReply);
        if (row != 1) {
            //打印日志
            log.warn(ResultCode.FAILED_CREATE.toString() + "articleReply id = " + articleReply.getId());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        //更新帖子回复数
        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setReplyCount(article.getReplyCount() + 1);
        int updateRow = articleMapper.updateByPrimaryKeySelective(updateArticle);
        if (updateRow != 1) {
            //打印日志
            log.warn(ResultCode.FAILED_CREATE.toString() + "article id = " + article.getId());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
    }





}
