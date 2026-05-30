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















}
