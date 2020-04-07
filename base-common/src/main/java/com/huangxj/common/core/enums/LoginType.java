package com.huangxj.common.core.enums;


/**
 * @author huangxj
 */

public enum LoginType {

    PASSWORD("password", "密码登录"),
    CODE("code", "验证码登录"),
    MINIAPP("miniapp", "小程序登录")
    ;

    private final String code;

    private final String message;


    LoginType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
