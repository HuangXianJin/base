package com.huangxj.base.system.service;

import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.entity.Department;

/**
 * 部门表 服务类
 *
 * @author yuguohui
 * @date 2020-03-18
 */
public interface DepartmentService extends BaseService<Department> {

    /**
     * 检查冲突：
     * 同租户编码，机构唯一性校验
     * @param department 考号对象
     */
    void checkConflict(Department department);

    /**
     * 添加部门-用户关联
     * @param userId 用户id
     * @param departmentId 部门id
     */
    void saveDepartmentUser(Integer userId, Integer departmentId);

    /**
     * 根据用户，查询部门
     * @param userId 用户id
     * @return 部门id
     */
    Integer getDepartmentByUser(Integer userId);

}
