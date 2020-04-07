package com.huangxj.common.core.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.huangxj.common.core.constant.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author huangxj
 */
@ApiModel(value = "响应结果")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;

    /**
     * 是否处理成功
     */
    @ApiModelProperty(value = "是否处理成功")
    private boolean success = true;

    /**
     * 响应编码
     */
    @ApiModelProperty(value = "响应编码:0-请求处理成功")
    private int code = 200;
    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息")
    private String message;

    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    private String path;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * http状态码
     */
    private int httpStatus;

    /**
     * 附加数据
     */
    @ApiModelProperty(value = "附加数据")
    private Map<String, Object> extra;

    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间")
    private long timestamp = System.currentTimeMillis();

    public Result() {
        super();
    }


    public Result(boolean success, int code, String message, T data) {
        super();
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public Result(boolean success, int code, String message) {
        super();
        this.success = success;
        this.message = message;
        this.code = code;
    }


    public Result(int code, String message) {
        super();
        this.success = false;
        this.message = message;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }


    public Map<String, Object> getExtra() {
        return extra;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public T getData() {
        return data;
    }

    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    public int getHttpStatus() {
        return httpStatus;
    }

    public Result code(int code) {
        this.code = code;
        this.message = i18n(ErrorCode.getResultEnum(this.code).getMessage(), message);
        return this;
    }

    public Result isSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result msg(String message) {
        this.message = i18n(ErrorCode.getResultEnum(this.code).getMessage(), message);
        return this;
    }

    public Result data(T data) {
        this.data = data;
        return this;
    }

    public Result path(String path) {
        this.path = path;
        return this;
    }

    public Result httpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public Result put(String key, Object value) {
        if (this.extra == null) {
            this.extra = Maps.newHashMap();
        }
        this.extra.put(key, value);
        return this;
    }


    /**
     * 错误信息配置
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("error");

    /**
     * 提示信息国际化
     *
     * @param message
     * @param defaultMessage
     * @return
     */
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private static String i18n(String message, String defaultMessage) {
        return resourceBundle.containsKey(message) ? resourceBundle.getString(message) : defaultMessage;
    }

    /**
     * 请求成功
     *
     * @return
     */
    public static Result success() {
        return new Result().code(ErrorCode.OK.getCode()).msg(ErrorCode.OK.getMessage());
    }


    public static Result fail() {
        return new Result().isSuccess(false).code(ErrorCode.FAIL.getCode()).msg(ErrorCode.FAIL.getMessage());
    }


    /**
     * 操作失败
     *
     * @return
     */
    public static Result fail(String message) {
        return fail().message(message);
    }

}
