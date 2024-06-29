package com.louis.top_news.common;

/**
 * 统一返回结果的狀態和訊息
 */
public enum ResultCodeEnum {
    SUCCESS(200 , "success"), USERNAME_ERROR(501 , "usernameError"), PASSWORD_ERROR(503 , "passwordError"),
    NOTLOGIN(504 , "notLogin"), USERNAME_USED(505 , "usernameUsed");
    private Integer code;
    private String message;

    private ResultCodeEnum (Integer code , String message) {
        this.code    = code;
        this.message = message;
    }

    public Integer getCode () {
        return code;
    }

    public String getMessage () {
        return message;
    }
}
