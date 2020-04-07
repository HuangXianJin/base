package com.huangxj.base.system.service.impl;

import com.huangxj.common.core.exception.AlertException;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.common.core.utils.BeanConvertUtils;
import com.huangxj.common.core.utils.RandomValueUtils;
import com.huangxj.base.system.entity.App;
import com.huangxj.base.system.mapper.AppMapper;
import com.huangxj.base.system.service.AppService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static com.huangxj.common.core.constant.AppConstant.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.huangxj.common.core.constant.AppConstant.REFRESH_TOKEN_VALIDITY_SECONDS;

/**
 * 客户端应用 服务实现类
 *
 * @author huangxj
 * @date 2019-08-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AppServiceImpl extends BaseServiceImpl<AppMapper, App> implements AppService {

    JdbcClientDetailsService jdbcClientDetailsService;

    /**
     * 获取app和应用信息
     *
     * @return
     */
    @Override
    public BaseClientDetails getAppClientInfo(String clientId) {
        BaseClientDetails baseClientDetails = (BaseClientDetails) jdbcClientDetailsService.loadClientByClientId(clientId);
        if (baseClientDetails == null) {
            return null;
        }
        return baseClientDetails;
    }

    /**
     * 添加应用
     *
     * @param app
     * @return 应用信息
     */
    @Override
    public boolean save(App app) {
        String apiKey = RandomValueUtils.randomAlphanumeric(24);
        String secretKey = RandomValueUtils.randomAlphanumeric(32);
        app.setApiKey(apiKey);
        app.setSecretKey(secretKey);
        super.save(app);
        Map info = BeanConvertUtils.objectToMap(app);
        // 功能授权
        BaseClientDetails client = new BaseClientDetails();

        client.setClientId(app.getApiKey());
        client.setClientSecret(app.getSecretKey());
        client.setAdditionalInformation(info);
        client.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "client_credentials", "implicit", "refresh_token"));
        client.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
        client.setRefreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
        jdbcClientDetailsService.addClientDetails(client);
        return true;
    }

    /**
     * 修改应用
     *
     * @param app 应用
     * @return 应用信息
     */
    @Override
    public boolean updateById(App app) {
        super.updateById(app);
        // 修改客户端附加信息
        App appInfo = this.getById(app.getId());
        Map info = BeanConvertUtils.objectToMap(appInfo);
        BaseClientDetails client = (BaseClientDetails) jdbcClientDetailsService.loadClientByClientId(appInfo.getApiKey());
        client.setAdditionalInformation(info);
        jdbcClientDetailsService.updateClientDetails(client);
        return true;
    }

    /**
     * 重置秘钥
     *
     * @param id
     * @return
     */
    @Override
    public String restSecret(Integer id) {
        App appInfo = this.getById(id);
        if (appInfo == null) {
            throw new AlertException(id + "应用不存在!");
        }
        // 生成新的密钥
        String secretKey = RandomValueUtils.randomAlphanumeric(32);
        appInfo.setSecretKey(secretKey);
        appInfo.setModifyTime(new Date());
        updateById(appInfo);
        jdbcClientDetailsService.updateClientSecret(appInfo.getApiKey(), secretKey);
        return secretKey;
    }

    /**
     * 删除应用
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        App appInfo = this.getById(id);
        if (appInfo == null) {
            throw new AlertException(id + "应用不存在!");
        }
        // 移除应用权限
        super.removeById(id);
        jdbcClientDetailsService.removeClientDetails(appInfo.getApiKey());
        return true;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String apiKey = String.valueOf(RandomValueUtils.randomAlphanumeric(24));
        String secretKey = String.valueOf(RandomValueUtils.randomAlphanumeric(32));
        System.out.println("apiKey=" + apiKey);
        System.out.println("secretKey=" + secretKey);
        System.out.println("encodeSecretKey=" + encoder.encode(secretKey));
    }

}
