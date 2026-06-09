package com.bite.forum.controller;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.config.AppConfig;
import com.bite.forum.model.Article;
import com.bite.forum.model.ArticleReply;
import com.bite.forum.model.User;
import com.bite.forum.service.IArticleReplyService;
import com.bite.forum.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "回复接口")
@Slf4j
@RequestMapping("/reply")
public class ArticleReplyController {

    @Resource
    private IArticleReplyService articleReplyService;

    @Resource
    private IArticleService articleService;


    /*
    * 新增回复
     */
    @PostMapping("/create")
    @Operation(summary = "新增回复")
    public AppResult create(HttpServletRequest request,
                            @Param("帖子id") Long articleId,
                            @Param("回复内容") String content) {
        //获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //判断用户是否被禁言
        if (user.getState() != 0) {
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        //获取帖子信息
        Article article = articleService.selectById(articleId);
        //校验帖子信息
        if (article == null) {
            log.warn(ResultCode.FAILED_ARTICLE_NOT_EXISTS.toString());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_NOT_EXISTS);
        }
        if (article.getState() != 0 || article.getDeleteState() != 0) {
            log.warn(ResultCode.FAILED_ARTICLE_BANNED.toString());
            return AppResult.failed(ResultCode.FAILED_ARTICLE_BANNED);
        }
        //构建数据
        ArticleReply articleReply = new ArticleReply();
        articleReply.setArticleId(articleId);
        articleReply.setPostUserId(user.getId());
        articleReply.setContent(content);
        articleReply.setReplyId(null);
        articleReply.setReplyUserId(null);
        //调用业务层
        articleReplyService.create(articleReply);
        //返回结果
        return AppResult.success();
    }

}
