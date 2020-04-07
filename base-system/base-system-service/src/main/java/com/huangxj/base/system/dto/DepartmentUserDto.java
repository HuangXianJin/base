package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.DepartmentUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 部门-用户关联表数据传输对象实体类
 *
 * @author yuguohui
 * @since 2020-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentUserDto extends DepartmentUser implements Serializable {

}
