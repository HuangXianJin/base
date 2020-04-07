package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huangxj.common.core.security.AuthHelper;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.entity.DepartmentPermission;
import com.huangxj.base.system.mapper.DepartmentPermissionMapper;
import com.huangxj.base.system.service.DepartmentPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门权限表 服务实现类
 *
 * @author yuguohui
 * @date 2020-03-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentPermissionServiceImpl extends BaseServiceImpl<DepartmentPermissionMapper, DepartmentPermission> implements DepartmentPermissionService {

    @Override
    public void saveDepartmentPermission(Integer departmentId, String departmentPermissions) {
        if (Objects.isNull(departmentId) || StringUtils.isBlank(departmentPermissions)) {
            return;
        }
        //先清空原有数据
        this.remove(new LambdaQueryWrapper<DepartmentPermission>()
                .eq(DepartmentPermission::getDepartmentId, departmentId));
        LinkedList<DepartmentPermission> permissions = new LinkedList<>();
        Arrays.asList(departmentPermissions.split(",")).forEach(permission -> {
            DepartmentPermission departmentPermission = new DepartmentPermission();
            departmentPermission.setDepartmentId(departmentId);
            departmentPermission.setDepartmentPermission(permission);
            departmentPermission.setCreateId(AuthHelper.getUserId());
            departmentPermission.setCreateTime(new Date());
            permissions.add(departmentPermission);
        });
        this.saveBatch(permissions);
    }

    @Override
    public List listByDepartmentId(Integer departmentId) {
        if (Objects.isNull(departmentId)){
            return Collections.emptyList();
        }
        return this.list(new LambdaQueryWrapper<DepartmentPermission>()
                .eq(DepartmentPermission::getDepartmentId, departmentId))
                .stream()
                .map(DepartmentPermission::getDepartmentPermission)
                .collect(Collectors.toList());
    }
}
