package com.bite.forum.dao;

import com.bite.forum.model.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    int insert(Article row);

    int insertSelective(Article row);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article row);

    int updateByPrimaryKeyWithBLOBs(Article row);

    int updateByPrimaryKey(Article row);

    /*
    查询所有帖子列表
     */
    List<Article> selectAll();

    /*
    查询指定板块的帖子列表 - 根据板块ID
     */
    List<Article> selectAllByBoardId(Long boardId);


}