package com.huangxj.base.system.enums;

/**
 * @author huangxj
 */

public enum AuthorityType {

    /*
     * 时间段
     */
    API("com.huangxj.base.system.listener", "com.huangxj.base.system.listener"),
    MENU("menu", "菜单");

    private final String code;
    private final String message;

    AuthorityType(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCodeByMessage(String message) {
        for (com.huangxj.base.system.enums.AppType t : com.huangxj.base.system.enums.AppType.values()) {
            if (t.getMessage().equalsIgnoreCase(message)) {
                return t.getCode();
            }
        }
        return null;
    }

    public static String getMessageByCode(String code) {
        for (com.huangxj.base.system.enums.AppType t : com.huangxj.base.system.enums.AppType.values()) {
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
