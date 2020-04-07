package com.huangxj.base.system.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门权限表查询参数
 *
 * @author yuguohui
 * @since 2020-03-26
 */
@Data
public class DepartmentPermissionParam {

    @ApiModelProperty(value = "搜索关键字")
    private String filter;

    @ApiModelProperty(value = "部门id")
    private Integer departmentId;
}
