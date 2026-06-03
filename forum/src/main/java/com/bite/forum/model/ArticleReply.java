package com.bite.forum.model;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Data
public class ArticleReply {
    private Long id;

    private Long articleId; // 帖子Id

    private Long postUserId; // 发帖人

    private Long replyId; // 回复Id

    private Long replyUserId; // 回复人

    private String content; // 回复内容

    private Integer likeCount; // 点赞数

    private Byte state;

    private Byte deleteState;

    private Date createTime;

    private Date updateTime;
}