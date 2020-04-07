package com.huangxj.base.security;

import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.security.AuthUserDetails;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @ClassName MyAccessDecisionManager
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-09-12 15:05
 * @Version V1.0
 **/
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //该url有配置权限,但是当然登录用户没有匹配到对应权限,则禁止访问
        AuthUserDetails user = (AuthUserDetails) authentication.getPrincipal();
        if ("super".equals(user.getAccountType())) {
            return;
        }
        String needPermission;
        for (ConfigAttribute c : configAttributes) {
            //获得所需的权限
            needPermission = c.getAttribute();
            //遍历用户拥有的权限与URL所需的权限进行对比
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needPermission.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        throw new AccessDeniedException(ErrorCode.ACCESS_DENIED.getMessage());
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
