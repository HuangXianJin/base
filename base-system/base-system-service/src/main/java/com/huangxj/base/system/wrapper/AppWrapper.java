package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.App;
import com.huangxj.base.system.vo.AppVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 客户端应用包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-08
 */
@Component
public class AppWrapper extends BaseEntityWrapper<App, AppVo>  {

	@Override
	public AppVo entityVO(App app) {
		AppVo appVo =  BeanConverter.convert(app, AppVo.class);

		return appVo;
	}

}
