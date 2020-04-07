package com.huangxj.base.system.wrapper;

import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.base.system.entity.Role;
import com.huangxj.base.system.entity.Tenant;
import com.huangxj.base.system.entity.User;
import com.huangxj.base.system.service.DepartmentPermissionService;
import com.huangxj.base.system.service.DepartmentService;
import com.huangxj.base.system.service.RoleService;
import com.huangxj.base.system.service.TenantService;
import com.huangxj.base.system.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 用户信息包装类,返回视图层所需的字段
 *
 * @author huangxj
 * @since 2019-08-08
 */
@Component
public class UserWrapper extends BaseEntityWrapper<User, UserVo> {

    @Autowired
    TenantService tenantService;

    @Autowired
    RoleService roleService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentPermissionService departmentPermissionService;

    @Override
    public UserVo entityVO(User user) {
        if (user == null) {
            return null;
        }
        UserVo userVo = BeanConverter.convert(user, UserVo.class);
        Tenant tenant = tenantService.getTenantByCode(userVo.getTenantCode());
        if (tenant != null) {
            userVo.setTenantName(tenant.getTenantName());
            userVo.setTenantExpireTime(tenant.getExpireTime());
        }
        userVo.setRoleIds(roleService.listRoleByUser(user.getId()).stream().map(Role::getId).collect(Collectors.toList()));
        userVo.setDepartmentId(departmentService.getDepartmentByUser(user.getId()));
        userVo.setDepartmentPermissionList(departmentPermissionService.listByDepartmentId(userVo.getDepartmentId()));
        return userVo;
    }

}
