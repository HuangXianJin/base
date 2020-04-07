package com.huangxj.base.system.service.impl;

import com.huangxj.base.system.entity.RoleUser;
import com.huangxj.base.system.mapper.RoleUserMapper;
import com.huangxj.base.system.service.RoleUserService;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

/**
 * 系统角色-用户关联 服务实现类
 *
 * @author huangxj
 * @date 2019-08-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class RoleUserServiceImpl extends BaseServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {

}
