package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.Role;
import com.huangxj.base.system.vo.RoleVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 系统角色-基础信息包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Component
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVo>  {

	@Override
	public RoleVo entityVO(Role role) {
		RoleVo roleVo =  BeanConverter.convert(role, RoleVo.class);

		return roleVo;
	}

}
