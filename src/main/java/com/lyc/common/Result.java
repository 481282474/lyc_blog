package com.lyc.common;

import lombok.Getter;

import java.io.Serializable;

/**
 * 通用返回体
 * @author 刘怡畅
 * @date 2022/12/24 12:55
 */
@Getter
public class Result implements Serializable {

    private int code;   //状态码

    private String msg;     //错误信息

    private Object data;    //其他数据

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return new Result(200,"success",data);
    }

    public static Result fail() {
        return fail(500,"fail");
    }

    public static Result fail(int code, String message) {
        return new Result(code,message);
    }
}