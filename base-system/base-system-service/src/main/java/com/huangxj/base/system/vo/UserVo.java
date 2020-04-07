package com.huangxj.base.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户信息视图实体类
 *
 * @author huangxj
 * @since 2019-08-08
 */
@Data
@ApiModel(value = "UserVO对象", description = "用户信息")
public class UserVo {

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "租户编号")
    private String tenantCode;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "租户过期时间")
    private Date tenantExpireTime;

    @ApiModelProperty(value = "登陆账号")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户类型:super-超级管理员 normal-普通管理员")
    private String userType;

    @ApiModelProperty(value = "描述")
    private String userDesc;

    @ApiModelProperty(value = "状态:0-禁用 1-正常 2-锁定")
    private Integer status;

    @ApiModelProperty(value = "用户角色ids")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "机构id")
    private Integer departmentId;

    @ApiModelProperty(value = "机构权限List")
    private List departmentPermissionList;
}
