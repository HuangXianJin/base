package com.huangxj.base.system.service;

import com.github.qcloudsms.SmsSingleSenderResult;

/**
 * 短信服务 服务类
 *
 * @author yuguohui
 * @date 2020-03-01
 */
public interface SmsService  {

    /**
     *
     * Title: SmsSingleSender
     * </p>
     * Description:单条短信发送
     *
     * @param phoneNumber
     * @param content
     * @return
     */
    SmsSingleSenderResult send(String phoneNumber, String content);
}
