package com.huangxj.base.system.vo;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统角色-用户关联视图实体类
 *
 * @author huangxj
 * @since 2019-08-20
 */
@Data
@ApiModel(value = "RoleUserVO对象", description = "系统角色-用户关联")
public class RoleUserVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

}
