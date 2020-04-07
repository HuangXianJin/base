package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.base.system.entity.Tenant;
import com.huangxj.base.system.mapper.TenantMapper;
import com.huangxj.base.system.service.TenantService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 租户信息 服务实现类
 *
 * @author huangxj
 * @date 2019-08-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "TenantService")
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Override
    @Cacheable(key="'getTenantByCode/'+#code")
    public Tenant getTenantByCode(String code) {
        return this.baseMapper.selectOne(new QueryWrapper<Tenant>().lambda().eq(Tenant::getTenantCode, code));
    }
}
