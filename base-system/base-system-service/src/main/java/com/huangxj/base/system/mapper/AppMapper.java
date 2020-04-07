package com.huangxj.base.system.mapper;

import com.huangxj.base.system.entity.App;
import com.huangxj.common.core.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 客户端应用 Mapper 接口
 * @author huangxj
 * @date 2019-08-08
 */
@Mapper
public interface AppMapper extends MyBaseMapper<App> {

}
