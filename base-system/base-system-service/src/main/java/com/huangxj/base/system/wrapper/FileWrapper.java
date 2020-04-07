package com.huangxj.base.system.wrapper;

import com.huangxj.base.system.entity.SysFile;
import org.springframework.stereotype.Component;
import com.huangxj.base.system.vo.SysFileVo;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.common.core.utils.BeanConverter;

/**
 * 文件资源管理包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2020-02-20
 */
@Component
public class FileWrapper extends BaseEntityWrapper<SysFile, SysFileVo>  {

	@Override
	public SysFileVo entityVO(SysFile sysFile) {
		SysFileVo fileVo =  BeanConverter.convert(sysFile, SysFileVo.class);

		return fileVo;
	}

}
