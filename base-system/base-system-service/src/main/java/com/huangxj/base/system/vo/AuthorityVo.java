package com.huangxj.base.system.vo;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统权限-菜单权限、API权限视图实体类
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Data
@ApiModel(value = "AuthorityVO对象", description = "系统权限-菜单权限、API权限")
public class AuthorityVo {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "权限标识")
    private String authority;

    @ApiModelProperty(value = "菜单资源ID")
    private Integer menuId;

    @ApiModelProperty(value = "API资源ID")
    private Integer apiId;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
