package com.huangxj.base.system.enums;

import java.util.Objects;

/**
 * @author huangxj
 */

public enum UserStatus {

    /*
     * 用户状态:0-禁用 1-正常 2-锁定
     */
    DISABLE(0, "禁用"),
    NORMAL(1, "正常"),
    LOCKED(2, "锁定");

    private final Integer code;
    private final String message;

    UserStatus(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static Integer getCodeByMessage(String message) {
        for (UserStatus t : UserStatus.values()) {
            if (t.getMessage().equalsIgnoreCase(message)) {
                return t.getCode();
            }
        }
        return null;
    }

    public static String getMessageByCode(Integer code) {
        for (UserStatus t : UserStatus.values()) {
            if (Objects.equals(t.getCode(), code)) {
                return t.getMessage();
            }
        }
        return null;
    }


    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
