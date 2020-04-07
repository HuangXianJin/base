package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.huangxj.common.core.constant.SecurityConstants;
import com.huangxj.common.core.security.AuthAuthority;
import com.huangxj.common.core.security.AuthHelper;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.dto.ApiDto;
import com.huangxj.base.system.dto.MenuDto;
import com.huangxj.base.system.entity.*;
import com.huangxj.base.system.enums.AuthorityType;
import com.huangxj.base.system.mapper.AuthorityMapper;
import com.huangxj.base.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统权限-菜单权限、API权限 服务实现类
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "AuthorityService")
public class AuthorityServiceImpl extends BaseServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    MenuService menuService;
    @Autowired
    ApiService apiService;
    @Autowired
    AuthorityOwnerService authorityOwnerService;
    @Autowired
    UserService userService;

    /**
     * 获取用户已授权权限
     *
     * @param userId
     * @return
     */
    @Override
    @Cacheable(key = ("'findAuthorityByUser/'+#userId"))
    public List<AuthAuthority> findAuthorityByUser(Integer userId) {
        User user = userService.getById(userId);
        Set<AuthAuthority> authorities = Sets.newHashSet();
        List<Role> rolesList = roleService.listRoleByUser(userId);
        if (rolesList != null) {
            for (Role role : rolesList) {
                // 加入角色已授权
                List<AuthAuthority> roleGrantedAuthority = findAuthorityByRole(role.getId());
                if (roleGrantedAuthority != null && roleGrantedAuthority.size() > 0) {
                    authorities.addAll(roleGrantedAuthority);
                }
            }
        }
        List<AuthAuthority> tenantAuthorities = findAuthorityByTenant(user.getTenantCode());
        //用户权限和租户权限的交集
        authorities.retainAll(tenantAuthorities);
        //添加菜单的api权限
        Set<Integer> menuIds = Sets.newHashSet();
        menuIds.add(0);
        authorities.forEach(e -> {
            if (e.getMenuId() != null) {
                menuIds.add(e.getMenuId());
            }
        });
        authorities.addAll(findAuthorityByMenu(menuIds));
        // 权限去重;
        return Lists.newArrayList(authorities);
    }

    /**
     * 获取角色已授权权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<AuthAuthority> findAuthorityByRole(Integer roleId) {
        return this.baseMapper.selectAuthorityByRole(roleId);
    }

    /**
     * 获取菜单已授权权限
     *
     * @param menuIds
     * @return
     */
    @Override
    public List<AuthAuthority> findAuthorityByMenu(Collection<Integer> menuIds) {
        return this.baseMapper.selectAuthorityByMenu(menuIds);
    }


    @Override
    public List<AuthAuthority> findAuthorityByApp(Integer appId) {
        return this.baseMapper.selectAuthorityByApp(appId);
    }

    @Override
    @Cacheable(key = "'findAuthorityByTenant/'+#tenantCode")
    public List<AuthAuthority> findAuthorityByTenant(String tenantCode) {
        return this.baseMapper.selectAuthorityByTenantCode(tenantCode);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void grantAuthority(Integer menuId, Integer roleId, Integer appId, String tenantCode, Date expireTime, List<String> authoritys) {
        if (menuId == null && roleId == null && appId == null && StringUtils.isEmpty(tenantCode)) {
            return;
        }
        LambdaQueryWrapper<AuthorityOwner> wrapper = new QueryWrapper<AuthorityOwner>().lambda();
        if (roleId != null) {
            wrapper.eq(AuthorityOwner::getRoleId, roleId);
        }
        if (appId != null) {
            wrapper.eq(AuthorityOwner::getAppId, appId);
        }
        if (menuId != null) {
            wrapper.eq(AuthorityOwner::getMenuId, menuId);
        }
        if (StringUtils.isNotEmpty(tenantCode)) {
            wrapper.eq(AuthorityOwner::getTenantCode, tenantCode);
        }
        authorityOwnerService.remove(wrapper);
        Set<String> authorityIdsSet = new HashSet(authoritys);
        Date now = new Date();
        Integer userId = AuthHelper.getUserId();
        for (String authority : authorityIdsSet) {
            AuthorityOwner authorityOwner = new AuthorityOwner();
            authorityOwner.setRoleId(roleId);
            authorityOwner.setAppId(appId);
            authorityOwner.setMenuId(menuId);

            authorityOwner.setTenantCode(tenantCode);
            authorityOwner.setAuthority(authority);
            authorityOwner.setExpireTime(expireTime);
            authorityOwner.setCreateTime(now);
            authorityOwner.setCreateId(userId);
            authorityOwnerService.save(authorityOwner);
        }
    }

    /**
     * 获取api资源
     *
     * @param
     * @return
     */
    @Override
    public List<ApiDto> findApiAuthorityResource() {
        return baseMapper.selectApiAuthority();
    }

    /**
     * 获取Menu资源
     *
     * @param
     * @return
     */
    @Override
    public List<MenuDto> findMenuAuthorityResource() {
        return baseMapper.selectMenuAuthority();
    }

    /**
     * 保存或修改权限
     *
     * @param resourceId
     * @param resourceType
     * @return 权限Id
     */
    @Override
    public Authority saveOrUpdateAuthority(Integer resourceId, String resourceType) {
        Authority baseAuthority = getAuthority(resourceId, resourceType);
        String authority = null;
        if (baseAuthority == null) {
            baseAuthority = new Authority();
        }
        if (AuthorityType.MENU.getCode().equals(resourceType)) {
            Menu menu = menuService.getById(resourceId);
            authority = SecurityConstants.AUTHORITY_PREFIX_MENU + menu.getMenuCode();
            baseAuthority.setMenuId(resourceId);
            baseAuthority.setStatus(menu.getStatus());
        }

        if (AuthorityType.API.getCode().equals(resourceType)) {
            Api api = apiService.getById(resourceId);
            authority = SecurityConstants.AUTHORITY_PREFIX_API + api.getApiCode();
            baseAuthority.setApiId(resourceId);
            baseAuthority.setStatus(api.getStatus());
        }
        if (authority == null) {
            return null;
        }
        // 设置权限标识
        baseAuthority.setAuthority(authority);
        if (baseAuthority.getId() == null) {
            // 新增权限
            baseMapper.insert(baseAuthority);
        } else {
            // 修改权限
            baseMapper.updateById(baseAuthority);
        }
        return baseAuthority;
    }

    /**
     * 删除权限
     *
     * @param resourceId
     * @param resourceType
     * @return 权限Id
     */
    @Override
    @CacheEvict(value = "AuthorityService")
    public void removeAuthority(Integer resourceId, String resourceType) {
        if (resourceId == null || resourceType == null) {
            return;
        }
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<Authority>();

        if (AuthorityType.API.getCode().equals(resourceType)) {
            queryWrapper.lambda().eq(Authority::getApiId, resourceId);
        }
        if (AuthorityType.MENU.getCode().equals(resourceType)) {
            queryWrapper.lambda().eq(Authority::getMenuId, resourceId);
        }
        baseMapper.delete(queryWrapper);
    }


    /**
     * 获取权限
     *
     * @param resourceId
     * @param resourceType
     * @return
     */
    public Authority getAuthority(Integer resourceId, String resourceType) {
        if (resourceId == null || resourceType == null) {
            return null;
        }
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<Authority>();

        if (AuthorityType.API.getCode().equals(resourceType)) {
            queryWrapper.lambda().eq(Authority::getApiId, resourceId);
        }
        if (AuthorityType.MENU.getCode().equals(resourceType)) {
            queryWrapper.lambda().eq(Authority::getMenuId, resourceId);
        }
        return baseMapper.selectOne(queryWrapper);
    }

}
