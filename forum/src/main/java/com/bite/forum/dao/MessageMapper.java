package com.bite.forum.dao;

import com.bite.forum.model.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {
    int insert(Message row);

    int insertSelective(Message row);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message row);

    int updateByPrimaryKey(Message row);
}