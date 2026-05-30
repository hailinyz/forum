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

    @Override
    public List<Board> selectAllNormal() {
        return List.of();
    }




}
