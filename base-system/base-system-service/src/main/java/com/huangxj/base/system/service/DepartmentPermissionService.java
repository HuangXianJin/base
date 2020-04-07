package com.huangxj.base.system.service;

import com.huangxj.base.system.entity.DepartmentPermission;
import com.huangxj.common.core.service.BaseService;

import java.util.List;

/**
 * 部门权限表 服务类
 *
 * @author yuguohui
 * @date 2020-03-26
 */
public interface DepartmentPermissionService extends BaseService<DepartmentPermission> {

    /**
     * 保存部门权限
     * @param departmentId 部门id
     * @param departmentPermissions 部门权限List
     */
    void saveDepartmentPermission(Integer departmentId, String departmentPermissions);

    /**
     * 根据部门id，查询部门权限
     * @param departmentId 部门id
     * @return 部门权限List
     */
    List listByDepartmentId(Integer departmentId);
}
