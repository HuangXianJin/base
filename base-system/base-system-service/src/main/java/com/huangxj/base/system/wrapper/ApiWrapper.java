package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.Api;
import com.huangxj.base.system.vo.ApiVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 系统资源-API接口包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Component
public class ApiWrapper extends BaseEntityWrapper<Api, ApiVo>  {

	@Override
	public ApiVo entityVO(Api api) {
		ApiVo apiVo =  BeanConverter.convert(api, ApiVo.class);

		return apiVo;
	}

}
