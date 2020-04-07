package com.huangxj.base.system.wrapper;

import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import com.huangxj.base.system.entity.DepartmentPermission;
import com.huangxj.base.system.vo.DepartmentPermissionVo;
import org.springframework.stereotype.Component;

/**
 * 部门权限表包装类,返回视图层所需的字段
 *
 * @author yuguohui
 * @since 2020-03-26
 */
@Component
public class DepartmentPermissionWrapper extends BaseEntityWrapper<DepartmentPermission, DepartmentPermissionVo> {

    @Override
    public DepartmentPermissionVo entityVO(DepartmentPermission departmentPermission) {
        DepartmentPermissionVo departmentPermissionVo = BeanConverter.convert(departmentPermission, DepartmentPermissionVo.class);

        return departmentPermissionVo;
    }

}
