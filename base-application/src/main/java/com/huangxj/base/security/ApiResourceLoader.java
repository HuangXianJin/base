package com.huangxj.base.security;

import com.google.common.collect.Lists;
import com.huangxj.common.core.security.AuthorityResource;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.ApiDto;
import com.huangxj.base.system.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资源加载器
 *
 * @author huangxj
 */
@Slf4j
@Component
public class ApiResourceLoader implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AuthorityService authorityServiceClient;
    /**
     * 权限资源
     */
    private List<AuthorityResource> authorityResources;


    /**
     * 清空缓存并刷新
     */
    public void refresh() {
        this.authorityResources = loadAuthorityResources();
    }

    /**
     * 加载授权列表
     */
    public List<AuthorityResource> loadAuthorityResources() {
        List<AuthorityResource> resources = Lists.newArrayList();

        try {
            // 查询所有接口
            resources = loadResources();
            if (resources != null) {
                for (AuthorityResource item : resources) {
                    String path = item.getPath();
                    if (path == null) {
                        continue;
                    }
                    String fullPath = path;
                    item.setPath(fullPath);
                }
                log.info("=============加载动态权限:{}==============", resources.size());
            }
        } catch (Exception e) {
            resources = Lists.newArrayList();
            e.printStackTrace();
            log.error("加载动态权限错误:{}", e.getMessage());
        }
        return resources;
    }


    private List<AuthorityResource> loadResources() {
        List<ApiDto> apiDtoList = authorityServiceClient.findApiAuthorityResource();
        this.authorityResources = BeanConverter.convert(apiDtoList, AuthorityResource.class);
        return this.authorityResources;
    }

    public List<AuthorityResource> getAuthorityResources() {
        if (this.authorityResources == null || this.authorityResources.isEmpty()) {
            refresh();
        }
        return authorityResources;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent refreshEvent) {
        refresh();
    }
}
