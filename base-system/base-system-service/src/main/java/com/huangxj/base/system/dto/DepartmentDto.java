package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 部门表数据传输对象实体类
 *
 * @author yuguohui
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentDto extends Department implements Serializable {

}
