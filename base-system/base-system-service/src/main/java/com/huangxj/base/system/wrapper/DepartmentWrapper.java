package com.huangxj.base.system.wrapper;

import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.common.core.wrapper.BaseEntityWrapper;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import com.huangxj.base.system.entity.Department;
import com.huangxj.base.system.vo.DepartmentVo;

import java.util.List;

/**
 * 部门表包装类,返回视图层所需的字段
 *
 * @author yuguohui
 * @since 2020-03-18
 */
@Component
@AllArgsConstructor
public class DepartmentWrapper extends BaseEntityWrapper<Department, DepartmentVo> {

	@Override
	public DepartmentVo entityVO(Department department) {
		DepartmentVo departmentVo =  BeanConverter.convert(department, DepartmentVo.class);

		return departmentVo;
	}

    /**
     * 转换成树形结构
     * @param departments 部门List
     * @return 部门树
     */
	public List toTree(List<Department> departments){
        List<DepartmentVo> departmentVos = BeanConverter.convert(departments, DepartmentVo.class);
        return BeanConverter.listToTree(null, departmentVos, DepartmentVo.class);
    }

}
