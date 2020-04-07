package com.huangxj.common.core.exception;

/**
 * 提示消息异常
 *
 * @author admin
 */
public class AlertException extends BaseException {
    private static final long serialVersionUID = 4908906410210213271L;

    public AlertException() {
    }

    public AlertException(String msg) {
        super(msg);
    }

    public AlertException(int code, String msg) {
        super(code, msg);
    }

    public AlertException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
