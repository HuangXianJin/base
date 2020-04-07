package com.huangxj.base.system.mapper;

import com.huangxj.base.system.entity.Tenant;
import com.huangxj.common.core.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 租户信息 Mapper 接口
 * @author huangxj
 * @date 2019-08-08
 */
@Mapper
public interface TenantMapper extends MyBaseMapper<Tenant> {

}
