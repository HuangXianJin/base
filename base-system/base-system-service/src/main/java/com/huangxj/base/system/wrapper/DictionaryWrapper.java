package com.huangxj.base.system.wrapper;

import org.springframework.stereotype.Component;
import com.huangxj.base.system.entity.Dictionary;
import com.huangxj.base.system.vo.DictionaryVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-10-09
 */
@Component
public class DictionaryWrapper extends BaseEntityWrapper<Dictionary, DictionaryVo>  {

	@Override
	public DictionaryVo entityVO(Dictionary dictionary) {
		DictionaryVo dictionaryVo =  BeanConverter.convert(dictionary, DictionaryVo.class);

		return dictionaryVo;
	}

}
