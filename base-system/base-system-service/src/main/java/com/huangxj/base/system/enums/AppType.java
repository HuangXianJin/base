package com.huangxj.base.system.enums;

/**
 * @author huangxj
 */

public enum AppType {

    /*
     * 时间段
     */
    SERVER("server", "服务应用"),
    APP("app", "移动应用"),
    PC("pc", "网页应用");

    private final String code;
    private final String message;

    AppType(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCodeByMessage(String message) {
        for (AppType t : AppType.values()) {
            if (t.getMessage().equalsIgnoreCase(message)) {
                return t.getCode();
            }
        }
        return null;
    }

    public static String getMessageByCode(String code) {
        for (AppType t : AppType.values()) {
            if (t.getCode().equalsIgnoreCase(code)) {
                return t.getMessage();
            }
        }
        return null;
    }


    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
