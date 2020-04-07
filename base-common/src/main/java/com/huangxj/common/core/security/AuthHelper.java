package com.huangxj.common.core.security;


import com.huangxj.common.core.utils.BeanConvertUtils;
import com.huangxj.common.core.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 认证信息帮助类
 *
 * @author liuyadu
 */
@Slf4j
public class AuthHelper {

    /**
     * 获取认证用户信息
     *
     * @return
     */
    public static AuthUserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUser(authentication);
    }

    /**
     * 获取认证用户信息
     *
     * @return
     */
    public static AuthUserDetails getUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            OAuth2Request clientToken = oAuth2Authentication.getOAuth2Request();
            if (!oAuth2Authentication.isClientOnly()) {
                if (authentication.getPrincipal() instanceof Map) {
                    return BeanConvertUtils.mapToObject((Map) authentication.getPrincipal(), AuthUserDetails.class);
                }
                return BeanConvertUtils.copy(authentication.getPrincipal(), AuthUserDetails.class);
            } else {
                AuthUserDetails openUser = new AuthUserDetails();
                openUser.setClientId(clientToken.getClientId());
                openUser.setAuthorities(clientToken.getAuthorities());
                return openUser;
            }
        }
        return null;
    }


    /**
     * 更新AuthUser
     *
     * @param openUser
     */
    public static void updateAuthUser(TokenStore tokenStore, AuthUserDetails openUser) {
        Assert.notNull(openUser.getClientId(), "客户端ID不能为空");
        Assert.notNull(openUser.getUsername(), "用户名不能为空");
        // 动态更新客户端生成的token
        Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientIdAndUserName(openUser.getClientId(), openUser.getUsername());
        if (accessTokens != null && !accessTokens.isEmpty()) {
            for (OAuth2AccessToken accessToken : accessTokens) {
                // 由于没有set方法,使用反射机制强制赋值
                OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
                Authentication authentication = oAuth2Authentication.getUserAuthentication();
                ReflectionUtils.setFieldValue(authentication, "principal", openUser);
                ReflectionUtils.setFieldValue(authentication, "authorities", openUser.getAuthorities());
                ReflectionUtils.setFieldValue(oAuth2Authentication, "authorities", openUser.getAuthorities());
                // 重新保存
                tokenStore.storeAccessToken(accessToken, oAuth2Authentication);
            }
        }
    }


    /***
     * 更新客户端权限
     * @param tokenStore
     * @param clientId
     * @param authorities
     */
    public static void updateAuthClientAuthorities(TokenStore tokenStore, String clientId, Collection<? extends GrantedAuthority> authorities) {
        // 动态更新客户端生成的token
        Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientId(clientId);
        if (accessTokens != null && !accessTokens.isEmpty()) {
            Iterator<OAuth2AccessToken> iterator = accessTokens.iterator();
            while (iterator.hasNext()) {
                OAuth2AccessToken token = iterator.next();
                OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
                // 由于没有set方法,使用反射机制强制赋值
                ReflectionUtils.setFieldValue(oAuth2Authentication, "authorities", authorities);
                // 重新保存
                tokenStore.storeAccessToken(token, oAuth2Authentication);
            }
        }
    }


    /**
     * 获取认证用户Id
     *
     * @return
     */
    public static Integer getUserId() {
        AuthUserDetails authUserDetails = getUser();
        return authUserDetails != null ? authUserDetails.getUserId() : null;
    }

    /**
     * 获取认证用户的机构权限
     * @return 机构权限List
     */
    public static List getUserDepartmentPermission(){
        return (List) getUser().getAttrs().get("departmentPermission");
    }

    /**
     * 是否拥有权限
     *
     * @param authority
     * @return
     */
    public static Boolean hasAuthority(String authority) {
        AuthUserDetails auth = getUser();
        if (auth == null) {
            return false;
        }
        if (AuthorityUtils.authorityListToSet(auth.getAuthorities()).contains(authority)) {
            return true;
        }
        return false;
    }

}
