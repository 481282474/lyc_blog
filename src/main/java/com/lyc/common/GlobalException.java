package com.lyc.common;

/**
 * 全局异常处理类,运行时异常
 * @author 刘怡畅
 * @date 2022/12/22 17:33
 */

public class GlobalException extends RuntimeException {

    private int code;

    private String message;

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
