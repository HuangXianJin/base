package com.huangxj.base.system.service;

import com.huangxj.base.system.entity.App;
import com.huangxj.common.core.service.BaseService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * 客户端应用 服务类
 *
 * @author huangxj
 * @date 2019-08-08
 */
public interface AppService extends BaseService<App> {

    /**
     *  获取app和应用信息
     * @param clientId
     * @return
     */
    BaseClientDetails getAppClientInfo(String clientId);

    /**
     *
     * @param id
     * @return
     */
    String restSecret(Integer id);

}
