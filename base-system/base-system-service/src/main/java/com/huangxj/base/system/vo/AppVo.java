package com.huangxj.base.system.vo;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户端应用视图实体类
 *
 * @author huangxj
 * @since 2019-08-08
 */
@Data
@ApiModel(value = "AppVO对象", description = "客户端应用")
public class AppVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "API访问key")
    private String apiKey;

    @ApiModelProperty(value = "API访问密钥")
    private String secretKey;

    @ApiModelProperty(value = "app名称")
    private String appName;

    @ApiModelProperty(value = "app英文名称")
    private String appNameEn;

    @ApiModelProperty(value = "应用图标")
    private String appIcon;

    @ApiModelProperty(value = "app类型:server-服务应用 app-手机应用 pc-PC网页应用 wap-手机网页应用")
    private String appType;

    @ApiModelProperty(value = "app描述")
    private String appDesc;

    @ApiModelProperty(value = "移动应用操作系统:ios-苹果 android-安卓")
    private String appOs;

    @ApiModelProperty(value = "官网地址")
    private String website;

    @ApiModelProperty(value = "开发者ID:默认为0")
    private Long developerId;

    @ApiModelProperty(value = "状态:0-无效 1-有效")
    private Integer status;

    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

}
