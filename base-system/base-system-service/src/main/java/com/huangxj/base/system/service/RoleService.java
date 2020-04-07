package com.huangxj.base.system.service;

import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.entity.Role;

import java.util.List;

/**
 * 系统角色-基础信息 服务类
 *
 * @author huangxj
 * @date 2019-08-19
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 获取用户的角色列表
     *
     * @param userId
     * @return
     */
    List<Role> listRoleByUser(Integer userId);

    /**
     * 用户添加角色
     *
     * @param userId
     * @param roles
     * @return
     */
    void saveUserRoles(Integer userId, List<Integer> roles);
}
