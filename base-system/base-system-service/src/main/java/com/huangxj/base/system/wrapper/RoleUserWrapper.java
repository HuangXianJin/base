package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.RoleUser;
import com.huangxj.base.system.vo.RoleUserVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 系统角色-用户关联包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-20
 */
@Component
public class RoleUserWrapper extends BaseEntityWrapper<RoleUser, RoleUserVo>  {

	@Override
	public RoleUserVo entityVO(RoleUser roleUser) {
		RoleUserVo roleUserVo =  BeanConverter.convert(roleUser, RoleUserVo.class);

		return roleUserVo;
	}

}
