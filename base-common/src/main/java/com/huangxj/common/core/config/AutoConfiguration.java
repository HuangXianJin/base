package com.huangxj.common.core.config;

import com.huangxj.common.core.exception.GlobalExceptionHandler;
import com.huangxj.common.core.exception.RestResponseErrorHandler;
import com.huangxj.common.core.http.AuthRestTemplate;
import com.huangxj.common.core.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName AutoConfiguration
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-08-14 15:29
 * @Version V1.0
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({CommonProperties.class})
public class AutoConfiguration {

    /**
     * 默认加密配置
     *
     * @return
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("bean [{}]", encoder);
        return encoder;
    }

    /**
     * Spring上下文工具配置
     *
     * @return
     */
    @Bean
    public SpringContextHolder springContextHolder() {
        SpringContextHolder holder = new SpringContextHolder();
        log.info("bean [{}]", holder);
        return holder;
    }

    /**
     * 统一异常处理配置
     *
     * @return
     */
    @Bean
    public GlobalExceptionHandler exceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        log.info("bean [{}]", exceptionHandler);
        return exceptionHandler;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //设置自定义ErrorHandler
        restTemplate.setErrorHandler(new RestResponseErrorHandler());
        return restTemplate;
    }


    /**
     * 自定义请求类
     *
     * @param
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthRestTemplate.class)
    public AuthRestTemplate openRestTemplate() {
        AuthRestTemplate restTemplate = new AuthRestTemplate();
        //设置自定义ErrorHandler
        restTemplate.setErrorHandler(new RestResponseErrorHandler());
        log.info("bean [{}]", restTemplate);
        return restTemplate;
    }

}
