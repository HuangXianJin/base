package com.huangxj.base.system.service;

import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.entity.Tenant;

/**
 * 租户信息 服务类
 *
 * @author huangxj
 * @date 2019-08-08
 */
public interface TenantService extends BaseService<Tenant> {

    /**
     * 获取租户
     * @param code
     * @return
     */
    Tenant getTenantByCode(String code);
}
