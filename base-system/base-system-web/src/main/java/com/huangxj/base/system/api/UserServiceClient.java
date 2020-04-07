package com.huangxj.base.system.api;

import com.huangxj.common.core.model.Result;
import com.huangxj.base.system.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户 com.huangxj.base.system.listener
 *
 * @author huangxj
 * @since 2019-08-08
 */
public interface UserServiceClient {

    /**
     * 获取客户端信息
     *
     * @param username
     * @return
     */
    @GetMapping("/user/username/{username}")
    Result<UserVo> getUserByUsername(@PathVariable("username") String username);

}
