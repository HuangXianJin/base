package com.huangxj.base.system.service.impl;

import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.base.system.entity.DepartmentUser;
import com.huangxj.base.system.mapper.DepartmentUserMapper;
import com.huangxj.base.system.service.DepartmentUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门-用户关联表 服务实现类
 *
 * @author yuguohui
 * @date 2020-03-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class DepartmentUserServiceImpl extends BaseServiceImpl<DepartmentUserMapper, DepartmentUser> implements DepartmentUserService {

}
