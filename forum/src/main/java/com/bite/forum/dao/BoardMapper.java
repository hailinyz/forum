package com.bite.forum.dao;

import com.bite.forum.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insert(Board row);

    int insertSelective(Board row);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board row);

    int updateByPrimaryKey(Board row);

    /*
    查询前 N个正常状态的版块
     */
    List<Board> selectByNum(Integer num);

    /*
    查询所有正常状态的版块
     */
    List<Board> selectAllNormal ();

}