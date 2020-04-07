package com.huangxj.common.core.security;

import lombok.Data;

import java.io.Serializable;

/**
 * 资源权限
 *
 * @author liuyadu
 */
@Data
public class AuthorityResource implements Serializable {
    private static final long serialVersionUID = -320031660125425711L;

    /**
     * 权限ID
     */
    private Integer authorityId;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 访问方法
     */
    private String requestMethod;

    /**
     * 权限标识
     */
    private String authority;

    /**
     * 是否身份认证
     */
    private Integer isAuth;

    /**
     * 是否公开访问
     */
    private Integer isOpen;

    /**
     * 服务名称
     */
    private String serviceId;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 资源状态
     */
    private Integer status;

}
