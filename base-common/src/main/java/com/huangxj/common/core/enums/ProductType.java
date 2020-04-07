package com.huangxj.common.core.enums;

/**
 * huangxj-base
 * 2019/11/12 20:29
 *
 * @author hjx
 * @since
 **/
public enum ProductType {

    PRODUCT_BADGE("PRODUCT_BADGE", "电子校徽");

    private final String code;

    private final String message;


    ProductType(String code, String message) {
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
