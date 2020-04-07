package com.huangxj.base.system.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门表查询参数
 *
 * @author yuguohui
 * @since 2020-03-18
 */
@Data
public class DepartmentParam {

    @ApiModelProperty(value = "搜索关键字")
    private String filter;

    @ApiModelProperty(value = "租户编号")
    private String tenantCode;
}
