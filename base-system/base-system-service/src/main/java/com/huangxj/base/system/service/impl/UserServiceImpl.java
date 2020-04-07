package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangxj.common.core.exception.AlertException;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.base.system.entity.User;
import com.huangxj.base.system.mapper.UserMapper;
import com.huangxj.base.system.service.RoleService;
import com.huangxj.base.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户信息 服务实现类
 *
 * @author huangxj
 * @date 2019-08-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    /**
     * 依据登录名查询系统用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, username));
    }

    /**
     * 创建用户
     *
     * @param user
     */
    @Override
    public void createUser(User user) {
        if (StringUtils.isBlank(user.getUserName())) {
            throw new AlertException("用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new AlertException("密码不能为空");
        }
        if (getUserByUsername(user.getUserName()) != null) {
            throw new AlertException("用户名:" + user.getUserName() + "已存在!");
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
       // user.setTenantCode(AuthHelper.getUser().getTenantCode());
        user.setUserType("normal");
        this.save(user);
    }

    /**
     * 登录
     *
     * @param username
     */
    public UserDetails login(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        return null;
    }

}
