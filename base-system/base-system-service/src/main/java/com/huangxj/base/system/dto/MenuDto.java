package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统资源-菜单信息数据传输对象实体类
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuDto extends Menu {

    private Integer authorityId;

    private String authority;
}
