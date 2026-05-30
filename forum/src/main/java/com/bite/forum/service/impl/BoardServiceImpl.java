package com.bite.forum.service.impl;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.dao.BoardMapper;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.Board;
import com.bite.forum.service.IBoradService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements IBoradService {

    @Resource
    private BoardMapper boardMapper;


    @Override
    public List<Board> selectByNum(Integer num) {
        //非空校验
        if (num <= 0) {
            //打印日志
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        //调用DAO查询数据库中的数据
        List<Board> result = boardMapper.selectByNum(num);
        //返回结果
        return result;
    }


    /*
    更新板块中的帖子数据
     */
    @Override
    public void addOneArticleCountById(Long id) {
        //非空校验
        if (id == null || id <= 0) {
            //打印日志
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }
        //查询对应板块中的帖子数
        Board board = boardMapper.selectByPrimaryKey(id);
        if (board == null) {
            //打印日志
            log.warn(ResultCode.ERROR_IS_NULL.toString() + "board id = " + id);
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        //更新帖子数量
        Board updateBoard = new Board();
        updateBoard.setId(board.getId());
        updateBoard.setArticleCount(board.getArticleCount() + 1);
        int row = boardMapper.updateByPrimaryKeySelective(updateBoard);
        if (row != 1) {
            //打印日志
            log.warn(ResultCode.FAILED_USER_ARTICLE_COUNT.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_ARTICLE_COUNT));
        }
    }

    /*
    根据id查询板块信息
     */
    @Override
    public Board selectById(Long id) {
        //非空校验
        if (id == null || id <= 0) {
            //打印日志
            log.warn(ResultCode.ERROR_IS_NULL.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }
        Board board = boardMapper.selectByPrimaryKey(id);
        return board;
    }


}
