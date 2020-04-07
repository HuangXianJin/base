package com.huangxj.common.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CommonProperties
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-08-05 19:18
 * @Version V1.0
 **/
@ConfigurationProperties(prefix = "common")
@Configuration
public class CommonProperties {

    /**
     * 网关客户端Id
     */
    private String clientId;
    /**
     * 网关客户端密钥
     */
    private String clientSecret;
}
