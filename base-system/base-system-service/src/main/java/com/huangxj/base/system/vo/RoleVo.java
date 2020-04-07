package com.huangxj.base.system.vo;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统角色-基础信息视图实体类
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Data
@ApiModel(value = "RoleVO对象", description = "系统角色-基础信息")
public class RoleVo {

    @ApiModelProperty(value = "角色ID")
    private Integer id;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "状态:0-无效 1-有效")
    private Integer status;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

}
