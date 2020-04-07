package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.DepartmentPermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 部门权限表数据传输对象实体类
 *
 * @author yuguohui
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentPermissionDto extends DepartmentPermission implements Serializable {

    @ApiModelProperty(value = "部门权限List，逗号分隔")
    private String departmentPermissions;

}
