package com.bite.forum.service.impl;

import com.bite.forum.model.Board;
import com.bite.forum.service.IBoradService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Resource
    private IBoradService boardService;

    @Resource
    private ObjectMapper objectMapper;

    @Test
    void selectByNum() {
        List<Board> boards = boardService.selectByNum(1);
        System.out.println( boards);
    }


    @Transactional
    @Test
    void addOneArticleCountById() {
        boardService.addOneArticleCountById(1L);
        System.out.println("更新成功");
    }

    @Test
    void selectById() throws JsonProcessingException {
        Board board = boardService.selectById(10L);
        System.out.println(objectMapper.writeValueAsString(board)); // 将board对象转为json字符串然后输出
    }
}