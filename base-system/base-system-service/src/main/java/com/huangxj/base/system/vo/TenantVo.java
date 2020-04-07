package com.huangxj.base.system.vo;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 租户信息视图实体类
 *
 * @author huangxj
 * @since 2019-08-08
 */
@Data
@ApiModel(value = "TenantVO对象", description = "租户信息")
public class TenantVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "租户编号")
    private String tenantCode;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "联系人")
    private String linkman;

    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "过期时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expireTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

}
