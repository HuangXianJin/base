package com.huangxj.base.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 系统资源-菜单信息视图实体类
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Data
@ApiModel(value = "MenuVO对象", description = "系统资源-菜单信息")
public class MenuVo implements Comparable<MenuVo> {

    @ApiModelProperty(value = "菜单Id")
    private Integer id;

    @ApiModelProperty(value = "权限Id")
    private Integer authorityId;

    @ApiModelProperty(value = "权限标识")
    private String authority;

    @ApiModelProperty(value = "父级菜单")
    private Integer parentId;

    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "描述")
    private String menuDesc;

    @ApiModelProperty(value = "请求路径")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "打开方式:_self窗口内,_blank新窗口")
    private String target;

    @ApiModelProperty(value = "优先级 越小越靠前")
    private Integer priority;

    @ApiModelProperty(value = "状态:0-无效 1-有效")
    private Integer status;

    @ApiModelProperty(value = "保留数据0-否 1-是 不允许删除")
    private Integer isPersist;

    @ApiModelProperty(value = "子菜单")
    private List<MenuVo> children;

    @Override
    public int compareTo(MenuVo o) {
        return this.getPriority() - o.getPriority();
    }
}
