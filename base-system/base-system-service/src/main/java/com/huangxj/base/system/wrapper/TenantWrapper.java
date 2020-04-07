package com.huangxj.base.system.wrapper;

import com.huangxj.common.core.utils.BeanConverter;
import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.Tenant;
import com.huangxj.base.system.vo.TenantVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;

/**
 * 租户信息包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-08
 */
@Component
public class TenantWrapper extends BaseEntityWrapper<Tenant, TenantVo>  {

	@Override
	public TenantVo entityVO(Tenant tenant) {
		TenantVo tenantVo =  BeanConverter.convert(tenant, TenantVo.class);

		return tenantVo;
	}

}
