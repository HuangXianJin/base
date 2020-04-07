package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huangxj.common.core.exception.AlertException;
import com.huangxj.common.core.security.AuthHelper;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.common.core.utils.EncryptUtils;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.entity.Department;
import com.huangxj.base.system.entity.DepartmentPermission;
import com.huangxj.base.system.entity.DepartmentUser;
import com.huangxj.base.system.mapper.DepartmentMapper;
import com.huangxj.base.system.service.DepartmentPermissionService;
import com.huangxj.base.system.service.DepartmentService;
import com.huangxj.base.system.service.DepartmentUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * 部门表 服务实现类
 *
 * @author yuguohui
 * @date 2020-03-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    private DepartmentPermissionService departmentPermissionService;

    private DepartmentUserService departmentUserService;

    @Override
    public void checkConflict(Department department) {
        Department one = this.getOne(new LambdaQueryWrapper<Department>()
                .eq(Department::getTenantCode, department.getTenantCode())
                .eq(Department::getDepartmentName, department.getDepartmentName()), false);
        if (Objects.nonNull(one) && !Objects.equals(one.getId(), department.getId())) {
            throw new AlertException("该机构已存在");
        }
    }

    @Override
    public boolean save(Department entity) {
        this.setDepartmentCode(entity);
        super.save(entity);
        //设置权限
        return this.setDepartmentPermission(entity.getParentId(), entity.getId(), entity.getDepartmentCode());
    }

    /**
     * 设置部门编码
     *
     * @param department 部门对象
     */
    private void setDepartmentCode(Department department) {
        //生成部门编码
        String md5 = EncryptUtils.md5Hex(String.valueOf(System.currentTimeMillis()));
        String departmentCode = md5.substring(md5.length() - 3);
        departmentCode = StringUtils.isNotBlank(department.getDepartmentCode())
                ? department.getDepartmentCode() + departmentCode
                : departmentCode;
        department.setDepartmentCode(departmentCode);
        //查询是否有重复
        int count = this.count(new LambdaQueryWrapper<Department>()
                .isNull(Objects.isNull(department.getParentId()), Department::getParentId)
                .eq(Objects.nonNull(department.getParentId()), Department::getParentId, department.getParentId())
                .eq(Department::getDepartmentCode, departmentCode));
        //有，则重复以上步骤，直到不重复为止
        if (count > 0) {
            this.setDepartmentCode(department);
        }
    }

    /**
     * 设置部门权限
     *
     * @param parentId                 父部门id
     * @param departmentId             部门id
     * @param departmentPermissionCode 权限
     * @return true\false
     */
    private boolean setDepartmentPermission(Integer parentId, Integer departmentId, String departmentPermissionCode) {
        //递归设置父部门权限
//        if (Objects.nonNull(parentId)) {
//            Department parent = this.getById(parentId);
//            this.setDepartmentPermission(parent.getParentId(), parent.getId(), departmentPermissionCode);
//        }
        //设置部门默认权限
        DepartmentPermission departmentPermission = new DepartmentPermission();
        departmentPermission.setDepartmentId(departmentId);
        departmentPermission.setDepartmentPermission(departmentPermissionCode);
        departmentPermission.setCreateId(AuthHelper.getUserId());
        departmentPermission.setCreateTime(new Date());
        return departmentPermissionService.save(departmentPermission);
    }

    @Override
    public void saveDepartmentUser(Integer userId, Integer departmentId) {
        if (Objects.isNull(userId) || Objects.isNull(departmentId)) {
            return;
        }
        //先清空原有数据
        departmentUserService.remove(new LambdaQueryWrapper<DepartmentUser>()
                .eq(DepartmentUser::getUserId, userId));

        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setUserId(userId);
        departmentUser.setDepartmentId(departmentId);
        departmentUser.setCreateId(AuthHelper.getUserId());
        departmentUser.setCreateTime(new Date());
        departmentUserService.save(departmentUser);
    }

    @Override
    public Integer getDepartmentByUser(Integer userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        DepartmentUser one = departmentUserService.getOne(new LambdaQueryWrapper<DepartmentUser>()
                .eq(DepartmentUser::getUserId, userId), false);
        return Objects.nonNull(one) ? one.getDepartmentId() : null;
    }
}
