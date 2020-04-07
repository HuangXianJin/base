package com.huangxj.common.core.controller;


import com.huangxj.common.core.security.AuthHelper;
import com.huangxj.common.core.security.AuthUserDetails;

/**
 * @Description: 基础Controller
 * @Author: huangxj
 * @Create: 2018/11/22
 * @Version: 1.0
 **/
public abstract class BaseController {


    protected Integer getUserId() {
        return AuthHelper.getUserId();
    }

    protected AuthUserDetails getUser() {
        return AuthHelper.getUser();
    }
}
