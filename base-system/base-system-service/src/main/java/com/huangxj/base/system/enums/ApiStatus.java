package com.huangxj.base.system.enums;

import java.util.Objects;

/**
 * @author huangxj
 */

public enum ApiStatus {

    /*
     * api状态:0-禁用 1-正常 2-锁定
     */
    DISABLE(0, "禁用"),
    NORMAL(1, "正常"),
    LOCKED(2, "维护中");

    private final Integer code;
    private final String message;

    ApiStatus(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static Integer getCodeByMessage(String message) {
        for (ApiStatus t : ApiStatus.values()) {
            if (t.getMessage().equalsIgnoreCase(message)) {
                return t.getCode();
            }
        }
        return null;
    }

    public static String getMessageByCode(Integer code) {
        for (ApiStatus t : ApiStatus.values()) {
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
