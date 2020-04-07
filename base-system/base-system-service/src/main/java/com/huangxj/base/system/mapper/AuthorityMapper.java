package com.huangxj.base.system.mapper;

import com.huangxj.common.core.mapper.MyBaseMapper;
import com.huangxj.common.core.security.AuthAuthority;
import com.huangxj.base.system.dto.ApiDto;
import com.huangxj.base.system.dto.MenuDto;
import com.huangxj.base.system.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统权限-菜单权限、API权限 Mapper 接口
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Mapper
public interface AuthorityMapper extends MyBaseMapper<Authority> {

    /**
     * 获取角色已授权权限
     *
     * @param roleId
     * @return
     */
    List<AuthAuthority> selectAuthorityByRole(@Param("roleId") Serializable roleId);

    /**
     * 获取角色已授权权限
     *
     * @param menuIds
     * @return
     */
    List<AuthAuthority> selectAuthorityByMenu(@Param("menuIds") Collection menuIds);

    /**
     * 获取应用已授权权限
     *
     * @param appId
     * @return
     */
    List<AuthAuthority> selectAuthorityByApp(@Param("appId") Serializable appId);

    /**
     * 获取应用已授权权限
     *
     * @param tenantCode
     * @return
     */
    List<AuthAuthority> selectAuthorityByTenantCode(@Param("tenantCode") String tenantCode);

    /**
     * 获取api资源
     *
     * @return
     */
    List<ApiDto> selectApiAuthority();

    /**
     * 获取api资源
     *
     * @return
     */
    List<MenuDto> selectMenuAuthority();

}
