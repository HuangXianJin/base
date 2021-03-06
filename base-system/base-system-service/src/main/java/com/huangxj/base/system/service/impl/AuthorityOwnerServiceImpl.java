package com.huangxj.base.system.service.impl;

import com.huangxj.base.system.entity.AuthorityOwner;
import com.huangxj.base.system.mapper.AuthorityOwnerMapper;
import com.huangxj.base.system.service.AuthorityOwnerService;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

/**
 * 系统权限-角色关联 服务实现类
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AuthorityOwnerServiceImpl extends BaseServiceImpl<AuthorityOwnerMapper, AuthorityOwner> implements AuthorityOwnerService {

}
