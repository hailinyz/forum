package com.bite.forum.controller;


import com.bite.forum.common.AppResult;
import com.bite.forum.exception.ApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试类相关接口")
@RestController
@RequestMapping("/test")
public class TestController {


    @Operation(summary = "测试接口1")
    @RequestMapping("/hello")
    public String hello(){
        return "hello Spring boot";
    }

    @Operation(summary = "测试接口2")
    @RequestMapping("/helloByName")
    public String helloByName(@Parameter(description = "姓名") String name){
        return "hello " + name;
    }


    @GetMapping("/exception")
    public AppResult testException() throws Exception{
        throw new Exception("这是一个Exception...");
    }

    @GetMapping("/applicationException")
    public AppResult testApplicationException(){
        throw  new ApplicationException("这是一个ApplicationException...");
    }

}
