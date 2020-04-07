package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.base.system.entity.Role;
import com.huangxj.base.system.entity.RoleUser;
import com.huangxj.base.system.mapper.RoleMapper;
import com.huangxj.base.system.service.RoleService;
import com.huangxj.base.system.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色-基础信息 服务实现类
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleUserService roleUserService;

    /**
     * 获取用户的角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> listRoleByUser(Integer userId) {
        List<RoleUser> sysUserRoles = roleUserService.list(new QueryWrapper<RoleUser>().lambda().eq(RoleUser::getUserId, userId));
        List<Integer> roleIds = sysUserRoles.stream().parallel().map(sysUserRole -> sysUserRole.getRoleId()).collect(Collectors.toList());
        List<Role> roles = new ArrayList<>();
        if (!ObjectUtils.isEmpty(sysUserRoles)) {
            roles = this.list(new QueryWrapper<Role>().lambda().in(Role::getId, roleIds));
        }
        return roles;
    }

    /**
     * 用户添加角色
     *
     * @param userId
     * @param roles
     * @return
     */
    @Override
    public void saveUserRoles(Integer userId, List<Integer> roles) {
        if (userId == null || roles == null) {
            return;
        }
        // 先清空,在添加
        roleUserService.remove(new QueryWrapper<RoleUser>().lambda().eq(RoleUser::getUserId, userId));
        List<RoleUser> roleUserList = new LinkedList<>();
        for (Integer roleId : roles) {
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(userId);
            roleUser.setRoleId(roleId);
            roleUserList.add(roleUser);
        }
        roleUserService.saveBatch(roleUserList);
    }

}
