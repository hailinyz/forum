package com.bite.forum.service;

import com.bite.forum.model.Board;

import java.util.List;

public interface IBoradService {

    List<Board> selectByNum (Integer num);

//    List<Board> selectAllNormal ();

    /*
    * 更新板块中的帖子数据
     */
    void addOneArticleCountById(Long id);

    /*
    * 根据id查询板块信息
     */
    Board selectById(Long  id);

}
