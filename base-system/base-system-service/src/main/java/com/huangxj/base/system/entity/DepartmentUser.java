package com.huangxj.base.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 部门-用户关联表
 *
 * @author yuguohui
 * @date 2020-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@TableName("sys_department_user")
@ApiModel(value="DepartmentUser对象", description="部门-用户关联表")
public class DepartmentUser implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "创建人ID")
    private Integer createId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人ID")
    private Integer modifyId;

    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "备用字段1")
    private String field1;

    @ApiModelProperty(value = "备用字段2")
    private String field2;


}
