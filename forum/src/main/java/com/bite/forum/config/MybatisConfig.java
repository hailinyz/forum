package com.bite.forum.config;

/*
配置Mybatis的扫描路径
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration //加入Spring容器
@MapperScan("com.bite.forum.dao") //扫描dao包下的所有接口
public class MybatisConfig {
}
