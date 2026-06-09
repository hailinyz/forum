package com.bite.forum.controller;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.config.AppConfig;
import com.bite.forum.model.Article;
import com.bite.forum.model.Board;
import com.bite.forum.model.User;
import com.bite.forum.service.IArticleService;
import com.bite.forum.service.IBoradService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "文章相关接口类")
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @Resource
    private IBoradService boradService;


    /*
    * 发布新帖子
     */
    @Operation(summary = "发布新帖子")
    @PostMapping("/create")
    public AppResult create(HttpServletRequest request,
                            Long boardId,
                            String title,
                            String content) {
        //校验用户是否禁言
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if (user.getState() == 1) { //表示用户已被禁言
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //板块校验
        Board board = boradService.selectById(boardId);
        if (board == null || board.getState() == 1 || board.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_BOARD_BANNED.toString() + "board id = " + boardId);
            return AppResult.failed(ResultCode.FAILED_CREATE);
        }
        //封装文章对象
        Article article = new Article();
        article.setBoardId(boardId); //板块id
        article.setUserId(user.getId()); //用户id
        article.setTitle(title); //标题
        article.setContent(content); //内容
        //调用Service
        articleService.create(article);
        return AppResult.success();
    }


    /*
    * 获取板帖子列表
     */
    @Operation(summary = "获取板帖子列表")
    @GetMapping("/getAllByBoardId")
    public AppResult<List<Article>> getAllByBoardId(@RequestParam(required = false) Long boardId) {

        List<Article> articles; //定义返回结果集

        if (boardId == null) {
            //查询所有
             articles = articleService.selectAll();
            } else {
                articles = articleService.selectByBoardId(boardId);
            }
        //非空判断
        if (articles == null) {
            articles = new ArrayList<>();
        }
        //响应结果
        return AppResult.success(articles);
    }


    /*
    * 根据帖子id获取帖子详情
     */
    @Operation(summary = "根据帖子id获取帖子详情")
    @GetMapping("/details")
    public AppResult<Article> getDetailById(HttpServletRequest request,
            @RequestParam(required = true) Long id) {

        //从Session中获取当前登录用户
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);

        //调用Service
        Article article = articleService.selectDetailById(id);
        //非空判断
        if (article == null) {
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        //判断当前用户是否是作者
        if (user.getId() == article.getUserId()) {
            //标识为作者
            article.setOwn(true);
        }
        //响应结果
        return AppResult.success(article);
    }

    /*
    * 修改帖子
     */
    @Operation(summary = "修改帖子")
    @PostMapping("/modify")
    public AppResult modify(HttpServletRequest request,
                            Long id,
                            String title,
                            String content) {
        //校验用户是否禁言
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if (user.getState() == 1) { //表示用户已被禁言
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //判断帖子是否存在
        Article article = articleService.selectById(id);
        if (article == null) {
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        //判断当前用户是否是作者
        if (user.getId() != article.getUserId()) {
            return AppResult.failed(ResultCode.FAILED_FORBIDDEN);
        }
        //判断帖子是否还能修改(帖子的状态)
        if (article.getState() == 1 || article.getDeleteState() == 1) {
            return AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED);
        }
        //调用Service完成更新修改
        articleService.modify(id, title, content);
        //打印日志
        log.info("修改帖子成功，帖子id = " + id + ", User id = " + user.getId());
        return AppResult.success();
    }



    /*
    * 点赞
     */
    @Operation(summary = "点赞")
    @PostMapping("/thumbsUp")
    public AppResult thumbsUp(HttpServletRequest request, Long id) {
        //获取当前用户
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //判断当前用户是否被禁言
        if (user.getState() == 1) { //表示用户已被禁言
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //更新点赞数
        articleService.thumbsUpById(id);
        return AppResult.success();
    }

    /*
    * 删除帖子
     */
    @Operation(summary = "删除帖子wolaile")
    @PostMapping("/delete")
    public AppResult delete(HttpServletRequest request, Long id) {
        //获取当前用户
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //判断当前用户是否被禁言
        if (user.getState() == 1) { //表示用户已被禁言
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //判断帖子是否存在
        Article article = articleService.selectById(id);
        if (article == null) {
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        //判断当前用户是否是作者
        if (user.getId() != article.getUserId()) {
            return AppResult.failed(ResultCode.FAILED_FORBIDDEN);
        }
        //删除帖子
        articleService.deleteById(id);
        return AppResult.success();
    }



















}
