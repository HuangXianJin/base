package com.huangxj.base.system.api;

import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.security.AuthAuthority;
import com.huangxj.base.system.dto.ApiDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 权限 com.huangxj.base.system.listener
 *
 * @author huangxj
 * @since 2019-08-20
 */
public interface AuthorityServiceClient {

    /**
     * 获取角色已分配权限
     *
     * @param roleId 角色ID
     * @return
     */
    @GetMapping("/authority/role/{roleId}")
    Result<List<AuthAuthority>> findAuthorityRole(@PathVariable Integer roleId);

    /**
     * 获取用户已分配权限
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/authority/user/{userId}")
    Result<List<AuthAuthority>> findAuthorityUser(@PathVariable Integer userId);


    /**
     * 获取应用已分配权限
     *
     * @param appId 应用Id
     * @return
     */
    @GetMapping("/authority/app/{appId}")
    Result<List<AuthAuthority>> findAuthorityApp(@PathVariable Integer appId);

    /**
     * 获取租户已分配权限
     *
     * @param tenantCode
     * @return
     */
    @GetMapping("/authority/tenant/{tenantCode}")
    Result<List<AuthAuthority>> findAuthorityTenant(@PathVariable String tenantCode);

    /**
     * 获取api资源
     *
     * @param
     * @return
     */
    @GetMapping("/authority/com.huangxj.base.system.api")
    Result<List<ApiDto>> findApiAuthorityResource();

}
