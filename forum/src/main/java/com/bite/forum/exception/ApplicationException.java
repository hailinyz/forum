package com.bite.forum.exception;

import com.bite.forum.common.AppResult;

/*
* 自定义异常类
 */
public class ApplicationException extends RuntimeException{

    //在异常中持有错误信息
    protected AppResult errorResult;

    /*
    * 构造方法
     */
    public ApplicationException(AppResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public AppResult getErrorResult() {
        return errorResult;
    }
}
