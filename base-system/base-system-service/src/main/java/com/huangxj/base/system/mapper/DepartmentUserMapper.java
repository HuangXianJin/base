package com.huangxj.base.system.mapper;

import com.huangxj.common.core.mapper.MyBaseMapper;
import com.huangxj.base.system.entity.DepartmentUser;
import org.apache.ibatis.annotations.Mapper;
/**
 * 部门-用户关联表 Mapper 接口
 * @author yuguohui
 * @date 2020-03-20
 */
@Mapper
public interface DepartmentUserMapper extends MyBaseMapper<DepartmentUser> {

}
