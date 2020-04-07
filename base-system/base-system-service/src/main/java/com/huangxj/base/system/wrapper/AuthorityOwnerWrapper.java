package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.AuthorityOwner;
import com.huangxj.base.system.vo.AuthorityOwnerVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 系统权限-角色关联包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Component
public class AuthorityOwnerWrapper extends BaseEntityWrapper<AuthorityOwner, AuthorityOwnerVo>  {

	@Override
	public AuthorityOwnerVo entityVO(AuthorityOwner authorityOwner) {
		AuthorityOwnerVo authorityOwnerVo =  BeanConverter.convert(authorityOwner, AuthorityOwnerVo.class);

		return authorityOwnerVo;
	}

}
