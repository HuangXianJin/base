package com.huangxj.base.system.enums;

import java.util.Objects;

/**
 * @author huangxj
 */

public enum FileType {

    /*
     * 文件类型
     */
     COMPANY_PIC("COMPANY_PIC", "company_pic");

    private final String code;
    private final String message;

    FileType(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCodeByMessage(String message) {
        for (FileType t : FileType.values()) {
            if (t.getMessage().equalsIgnoreCase(message)) {
                return t.getCode();
            }
        }
        return null;
    }

    public static String getMessageByCode(String code) {
        for (FileType t : FileType.values()) {
            if (Objects.equals(t.getCode(), code)) {
                return t.getMessage();
            }
        }
        return "attachment";
    }


    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
