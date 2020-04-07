package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.Tenant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户信息数据传输对象实体类
 *
 * @author huangxj
 * @since 2019-08-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantDto extends Tenant {

}
