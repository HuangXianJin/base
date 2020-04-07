package com.huangxj.base.system.service;

import com.huangxj.common.core.security.AuthAuthority;
import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.dto.ApiDto;
import com.huangxj.base.system.dto.MenuDto;
import com.huangxj.base.system.entity.Authority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 系统权限-菜单权限、API权限 服务类
 *
 * @author huangxj
 * @date 2019-08-19
 */
public interface AuthorityService extends BaseService<Authority> {

    /**
     * 获取用户已授权权限
     *
     * @param userId
     * @return
     */
    List<AuthAuthority> findAuthorityByUser(Integer userId);

    /**
     * 获取角色已授权权限
     *
     * @param roleId
     * @return
     */
    List<AuthAuthority> findAuthorityByRole(Integer roleId);

    /**
     * 获取菜单已授权权限
     *
     * @param menuId
     * @return
     */
    List<AuthAuthority> findAuthorityByMenu(Collection<Integer> menuIds);

    /**
     * 获取应用已授权权限
     *
     * @param appId
     * @return
     */
    List<AuthAuthority> findAuthorityByApp(Integer appId);

    /**
     * 获取租户已授权权限
     *
     * @param tenantCode
     * @return
     */
    List<AuthAuthority> findAuthorityByTenant(String tenantCode);

    /**
     * 授权
     *
     * @param menuId
     * @param roleId
     * @param appId
     * @param tenantCode
     * @param expireTime
     * @param authoritys
     */
    void grantAuthority(Integer menuId,Integer roleId, Integer appId, String tenantCode, Date expireTime, List<String> authoritys);

    /**
     * 获取api资源
     *
     * @param
     * @return
     */
    List<ApiDto> findApiAuthorityResource();

    /**
     * 获取Menu资源
     *
     * @param
     * @return
     */
    List<MenuDto> findMenuAuthorityResource();

    /**
     * 保存或修改权限
     *
     * @param resourceId
     * @param resourceType
     * @return 权限Id
     */
    Authority saveOrUpdateAuthority(Integer resourceId, String resourceType);

    /**
     * 删除权限
     *
     * @param resourceId
     * @param resourceType
     * @return 权限Id
     */
    void removeAuthority(Integer resourceId, String resourceType);


}
