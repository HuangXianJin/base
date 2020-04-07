package com.huangxj.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ers
 *
 * @author huangxj
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.huangxj")
@EnableCaching
@EnableSwagger2
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
