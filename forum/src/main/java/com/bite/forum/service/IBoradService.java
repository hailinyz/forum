package com.bite.forum.service;

import com.bite.forum.model.Board;

import java.util.List;

public interface IBoradService {

    List<Board> selectByNum (Integer num);

    List<Board> selectAllNormal ();

}
