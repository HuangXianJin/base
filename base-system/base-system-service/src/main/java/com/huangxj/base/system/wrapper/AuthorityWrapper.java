package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.Authority;
import com.huangxj.base.system.vo.AuthorityVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 系统权限-菜单权限、API权限包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Component
public class AuthorityWrapper extends BaseEntityWrapper<Authority, AuthorityVo>  {

	@Override
	public AuthorityVo entityVO(Authority authority) {
		AuthorityVo authorityVo =  BeanConverter.convert(authority, AuthorityVo.class);

		return authorityVo;
	}

}
