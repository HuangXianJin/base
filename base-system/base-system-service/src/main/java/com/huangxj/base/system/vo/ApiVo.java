package com.huangxj.base.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 系统资源-API接口视图实体类
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Data
@ApiModel(value = "ApiVO对象", description = "系统资源-API接口")

public class ApiVo implements Comparable<ApiVo> {

    @ApiModelProperty(value = "接口ID")
    private Integer id;

    @ApiModelProperty(value = "接口编码")
    private String apiCode;

    @ApiModelProperty(value = "接口名称")
    private String apiName;

    @ApiModelProperty(value = "接口分类:default-默认分类")
    private String apiCategory;

    @ApiModelProperty(value = "资源描述")
    private String apiDesc;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "响应类型")
    private String contentType;

    @ApiModelProperty(value = "服务ID")
    private String serviceId;

    @ApiModelProperty(value = "请求路径")
    private String path;

    @ApiModelProperty(value = "优先级")
    private Long priority;

    @ApiModelProperty(value = "状态:0-无效 1-有效")
    private Integer status;

    @ApiModelProperty(value = "是否需要认证: 0-无认证 1-身份认证 默认:1")
    private Integer isAuth;

    @ApiModelProperty(value = "是否公开: 0-内部的 1-公开的")
    private Integer isOpen;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    private Integer authorityId;

    private String authority;

    private List<ApiVo> children = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiVo)) {
            return false;
        }
        ApiVo apiVo = (ApiVo) o;
        return apiCode.equals(apiVo.apiCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiCode);
    }

    @Override
    public int compareTo(ApiVo o) {
        return this.getApiCode().compareTo(o.getApiCode());
    }
}
