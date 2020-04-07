package com.huangxj.base.system.vo;

import java.util.*;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统权限-角色关联视图实体类
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Data
@ApiModel(value = "AuthorityOwnerVO对象", description = "系统权限-角色关联")
public class AuthorityOwnerVo {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "权限ID")
    private Integer authorityId;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "客户端ID")
    private Integer appId;

    @ApiModelProperty(value = "租户编号")
    private String tenantCode;

    @ApiModelProperty(value = "过期时间:null表示长期")
    private Date expireTime;

    @ApiModelProperty(value = "权限")
    private List<String> authoritys;

}
