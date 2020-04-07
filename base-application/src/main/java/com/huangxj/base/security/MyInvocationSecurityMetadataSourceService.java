package com.huangxj.base.security;

import com.google.common.collect.Lists;
import com.huangxj.common.core.security.AuthorityResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName MyInvocationSecurityMetadataSourceService
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-09-12 15:08
 * @Version V1.0
 **/
@Service
@Slf4j
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Autowired
    ApiResourceLoader apiResourceLoader;

    private static final AntPathMatcher pathMatch = new AntPathMatcher();


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestPath = filterInvocation.getRequest().getRequestURI();
        String httpMethod = filterInvocation.getRequest().getMethod();
        if (permitAll(requestPath, httpMethod)) {
            return null;
        }
        return authoritiesRequired(requestPath, httpMethod);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {

        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


    private List<ConfigAttribute> authoritiesRequired(String requestPath, String httpMethod) {
        List<ConfigAttribute> authoritiesRequired = Lists.newArrayList();
        // 动态权限列表
        List<AuthorityResource> resources = apiResourceLoader.getAuthorityResources();
        for (AuthorityResource res : resources) {
            //http方法不相同
            if (StringUtils.isNotBlank(res.getRequestMethod()) && !Objects.equals(res.getRequestMethod().toUpperCase(), httpMethod.toUpperCase())) {
                continue;
            }
            String fullPath = res.getPath();
            // 无需认证,返回true
            if (StringUtils.isNotBlank(fullPath) && pathMatch.match(fullPath, requestPath)) {
                authoritiesRequired.add(new SecurityConfig(res.getAuthority()));
            }
        }
        return authoritiesRequired;
    }

    /**
     * 始终放行
     *
     * @param requestPath
     * @return
     */
    public boolean permitAll(String requestPath, String httpMethod) {
        // 动态权限列表
        List<AuthorityResource> resources = apiResourceLoader.getAuthorityResources();
        for (AuthorityResource res : resources) {

            Boolean isAuth = res.getIsAuth() != null && res.getIsAuth().intValue() == 1 ? true : false;
            String fullPath = res.getPath();

            //http方法相同
            if (StringUtils.isNotBlank(res.getRequestMethod()) && !Objects.equals(res.getRequestMethod().toUpperCase(), httpMethod.toUpperCase())) {
                continue;
            }
            // 无需认证,返回true
            if (!isAuth && StringUtils.isNotBlank(fullPath) && pathMatch.match(fullPath, requestPath)) {
                return true;
            }
        }
        return false;
    }

}
