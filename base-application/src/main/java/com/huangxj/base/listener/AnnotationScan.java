package com.huangxj.base.listener;

import com.huangxj.base.system.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 自定义注解扫描
 *
 * @author 更新api
 */
@Slf4j
@Component
public class AnnotationScan implements ApplicationListener<ApplicationReadyEvent> {

    private ExecutorService executorService = new ScheduledThreadPoolExecutor(2,
            new BasicThreadFactory.Builder().namingPattern("AnnotationScan-schedule-pool-%d" ).daemon(true).build());

    @Autowired
    ApiService apiService;

    /**
     * 初始化方法
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<com.huangxj.base.system.entity.Api> apis = apiService.scanApi();
        executorService.submit(() -> apiService.updateAnnotationScanApi(apis));
    }


}
