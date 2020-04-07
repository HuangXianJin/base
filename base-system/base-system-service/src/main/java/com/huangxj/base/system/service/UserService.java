package com.huangxj.base.system.service;

import com.huangxj.base.system.entity.User;
import com.huangxj.common.core.service.BaseService;

/**
 * 用户信息 服务类
 *
 * @author huangxj
 * @date 2019-08-08
 */
public interface UserService extends BaseService<User> {
    /**
     * 依据登录名查询系统用户信息
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 创建用户
     *
     * @param user
     */
    void createUser(User user);
}
