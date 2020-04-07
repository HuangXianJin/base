package com.huangxj.base.system.mapper;

import com.huangxj.base.system.entity.RoleUser;
import com.huangxj.common.core.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 系统角色-用户关联 Mapper 接口
 * @author huangxj
 * @date 2019-08-20
 */
@Mapper
public interface RoleUserMapper extends MyBaseMapper<RoleUser> {

}
