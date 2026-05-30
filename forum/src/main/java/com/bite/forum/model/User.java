package com.bite.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(description = "用户信息")
@Data
public class User {
    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @JsonIgnore
    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号码")
    private String phoneNum;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "性别：0-女，1-男")
    private Byte gender;

    @JsonIgnore //不参与JSON序列化
    @Schema(description = "密码盐值")
    private String salt;

    @Schema(description = "头像URL")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String avatarUrl;

    @Schema(description = "文章数量")
    private Integer articleCount;

    @Schema(description = "是否管理员：0-普通用户，1-管理员")
    private Byte isAdmin;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "状态：0-禁用，1-正常")
    private Byte state;

    @JsonIgnore //不参与JSON序列化
    @Schema(description = "删除状态：0-未删除，1-已删除")
    private Byte deleteState;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}