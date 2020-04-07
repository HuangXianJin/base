package com.huangxj.base.config.security;

import com.huangxj.common.core.constant.AppConstant;
import com.huangxj.common.core.exception.OAuth2WebResponseExceptionTranslator;
import com.huangxj.base.security.ClientDetailsServiceImpl;
import com.huangxj.base.security.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @ClassName AuthorizationServerConfiguration
 * @Description 平台认证服务器配置
 * @Author: huangxj
 * @Create: 2019-07-31 15:31
 * @Version V1.0
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceDetail userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
     DataSource dataSource;



    /**
     * 自定义获取客户端,为了支持自定义权限,
     */
    @Autowired
    ClientDetailsServiceImpl customClientDetailsService;


    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     *
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setClientDetailsService(customClientDetailsService);
        // token有效期自定义设置，90天
        tokenServices.setAccessTokenValiditySeconds(AppConstant.ACCESS_TOKEN_VALIDITY_SECONDS);
        // refresh_token 90天
        tokenServices.setRefreshTokenValiditySeconds(AppConstant.REFRESH_TOKEN_VALIDITY_SECONDS);
        return tokenServices;
    }
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }

    @Bean
    public TokenStore tokenStore() {
         return new JdbcTokenStore(dataSource);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        endpoints.tokenServices(defaultTokenServices());
        endpoints.exceptionTranslator(new OAuth2WebResponseExceptionTranslator());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }



}
