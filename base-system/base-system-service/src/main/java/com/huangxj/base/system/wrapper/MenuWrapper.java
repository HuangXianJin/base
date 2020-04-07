package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.Menu;
import com.huangxj.base.system.vo.MenuVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 系统资源-菜单信息包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Component
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVo>  {

	@Override
	public MenuVo entityVO(Menu menu) {
		MenuVo menuVo =  BeanConverter.convert(menu, MenuVo.class);

		return menuVo;
	}

}
