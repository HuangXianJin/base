package com.huangxj.base.security;

import com.huangxj.common.core.utils.BeanConvertUtils;
import com.huangxj.base.system.api.AppServiceClient;
import com.huangxj.base.system.api.AuthorityServiceClient;
import com.huangxj.base.system.entity.App;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-07-31 15:06
 * @Version V1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    JdbcClientDetailsService jdbcClientDetailsService;
    @Autowired
    AppServiceClient appService;
    @Autowired
    AuthorityServiceClient authorityServiceClient;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails details = jdbcClientDetailsService.loadClientByClientId(clientId);

        App app = appService.getAppByClientId(clientId).getData();
        if (details != null && app != null) {
            if (app.getStatus() != 1) {
                throw new ClientRegistrationException("客户端已被禁用");
            }
            BaseClientDetails baseClientDetails = BeanConvertUtils.copy(details, BaseClientDetails.class);
            baseClientDetails.setAuthorities(authorityServiceClient.findAuthorityApp(app.getId()).getData());
            return baseClientDetails;
        }
        throw new ClientRegistrationException("客户端不存在");
    }
}
