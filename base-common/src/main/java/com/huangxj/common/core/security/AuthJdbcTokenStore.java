package com.huangxj.common.core.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Date;

import static com.huangxj.common.core.constant.AppConstant.ACCESS_TOKEN_VALIDITY_SECONDS;

/**
 * @ClassName AuthJdbcTokenStore
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-08-12 15:05
 * @Version V1.0
 **/
public class AuthJdbcTokenStore extends JdbcTokenStore {
    public AuthJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication result = readAuthentication(token.getValue());
        if (result != null) {
            // 如果token没有失效  更新AccessToken过期时间
            DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) token;
            if (oAuth2AccessToken.getExpiration().getTime() > System.currentTimeMillis()) {
                oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + (ACCESS_TOKEN_VALIDITY_SECONDS * 1000L)));
            }
            //重新设置过期时间
            storeAccessToken(token, result);
        }
        return result;
    }

}
