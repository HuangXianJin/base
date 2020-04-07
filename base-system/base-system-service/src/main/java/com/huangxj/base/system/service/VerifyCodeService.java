package com.huangxj.base.system.service;

import com.huangxj.common.core.exception.BaseException;
import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.entity.VerifyCode;

/**
 * 验证码表 服务类
 *
 * @author yuguohui
 * @date 2020-03-01
 */
public interface VerifyCodeService extends BaseService<VerifyCode> {


    /**
     * 发送验证码
     * @param phone 手机号
     */
    void sendCode(String phone);

    /**
     * 校验验证码
     * @param phone 手机号
     * @param code 验证码
     */
    void verify(String phone, String code);

    /**
     * 作废验证码
     * @param phone 手机号
     */
    void invalidCode(String phone);

    /**
     * 获取验证码并失效
     * @param phone 手机号
     * @throws BaseException 错误信息
     */
    String checkCode(String phone);
}
