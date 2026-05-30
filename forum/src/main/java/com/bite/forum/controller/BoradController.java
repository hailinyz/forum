package com.bite.forum.controller;

import com.bite.forum.common.AppResult;
import com.bite.forum.common.ResultCode;
import com.bite.forum.exception.ApplicationException;
import com.bite.forum.model.Board;
import com.bite.forum.service.IBoradService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "板块类相关接口")
@Slf4j
@RestController
@RequestMapping("/board")
public class BoradController {

    // 从配置⽂件中获取主⻚中显⽰的版块个数，默认为9
    @Value("${bit-forum.index.board-num:9}")
    private Integer indexBoardNum;

    @Resource
    private IBoradService boardService;

    /*
    获取首页板块列表
     */
    @GetMapping("/topList")
    @Operation(summary = "获取首页板块列表")
    public AppResult<List<Board>> topList() {
        //调用Service查询结果
        List<Board> boards = boardService.selectByNum(indexBoardNum);
        //判断是否为空
        if (boards == null) {
            boards = new ArrayList<>();
        }
        //返回结果
        return AppResult.success(boards);
    }

    /*
    获取板块信息
     */
    @Operation(summary = "获取板块信息")
    @GetMapping("getById")
    public AppResult<Board> getById(@Nonnull Long id) {
        Board board = boardService.selectById(id);
        //对查询结果进行校验
        if (board == null || board.getDeleteState() == 1) {
            //打印日志
            log.warn(ResultCode.FAILED_BOARD_NOT_EXISTS.toString());
            //抛异常
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_NOT_EXISTS));
        }
        return AppResult.success(board);
    }






}
