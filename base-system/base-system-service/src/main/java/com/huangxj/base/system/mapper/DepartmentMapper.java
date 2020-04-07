package com.huangxj.base.system.mapper;

import com.huangxj.common.core.mapper.MyBaseMapper;
import com.huangxj.base.system.entity.Department;
import org.apache.ibatis.annotations.Mapper;
/**
 * 部门表 Mapper 接口
 * @author yuguohui
 * @date 2020-03-18
 */
@Mapper
public interface DepartmentMapper extends MyBaseMapper<Department> {

}
