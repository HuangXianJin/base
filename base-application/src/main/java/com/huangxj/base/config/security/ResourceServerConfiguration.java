package com.huangxj.base.config.security;

import com.huangxj.common.core.exception.AccessDeniedHandler;
import com.huangxj.common.core.exception.AuthenticationEntryPoint;
import com.huangxj.base.filter.CustomAuthenticationFilter;
import com.huangxj.base.handler.MyAuthenctiationFailureHandler;
import com.huangxj.base.handler.MyAuthenticationSuccessHandler;
import com.huangxj.base.security.MyFilterSecurityInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName ResourceServerConfiguration
 * @Description oauth2资源服务器配置
 * @Author: huangxj
 * @Create: 2019-07-31 15:31
 * @Version V1.0
 **/
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    protected static final String[] NON_TOKEN_BASED_AUTH_ENTRY_POINTS = new String[]{
            "/",
            "/miniapp/user/login",
            "/admin",
            "/upload/**",
            "/common/loadExcel",
            "/test/**",
            "/**/favicon.ico",
            "/**/index.html",
            "/**/static/**",
            "/webjars/**",
            "/v2/api-docs**",
            "/swagger**",
            "/swagger-resources/**",
            "/oauth/**",
            "/uaa-auth/**",
            "/basic/open/getInfectedArea",
            "/system/file/**",
            "/questionnaire/ownerInfo/ownerRegister",
            "/login"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //登录提交action，app会用到
                // 用户名登录地址
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                // 监控端点内部放行
                .antMatchers(NON_TOKEN_BASED_AUTH_ENTRY_POINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler())
                .authenticationEntryPoint(new AuthenticationEntryPoint())
                .and()
                .csrf().disable()
//                // 禁用httpBasic
                .httpBasic().disable();
        //自定义认证
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenctiationFailureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

}

