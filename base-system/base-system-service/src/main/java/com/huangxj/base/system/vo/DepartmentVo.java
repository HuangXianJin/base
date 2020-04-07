package com.huangxj.base.system.vo;

import java.util.*;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 部门表视图实体类
 *
 * @author yuguohui
 * @since 2020-03-18
 */
@Data
@ApiModel(value = "DepartmentVO对象", description = "部门表")
public class DepartmentVo implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "租户编号")
    private String tenantCode;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "1:启用,0禁用")
    private Integer status;

    @ApiModelProperty(value = "子列表")
    private List<DepartmentVo> children;

}
