package com.huangxj.base.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门权限表视图实体类
 *
 * @author yuguohui
 * @since 2020-03-26
 */
@Data
@ApiModel(value = "DepartmentPermissionVO对象", description = "部门权限表")
public class DepartmentPermissionVo implements Serializable {
    @ApiModelProperty(value = "部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "部门权限List，逗号分隔")
    private String departmentPermissions;

}
