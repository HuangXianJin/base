package com.huangxj.base.system.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.huangxj.base.system.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 短信服务 服务实现类
 *
 * @author yuguohui
 * @date 2020-03-01
 */
@Service
@Lazy
public class SmsServiceImpl implements SmsService {

    @Value("${sms.appid:}")
    private Integer appid;
    @Value("${sms.appkey:}")
    private String appkey;

    /**
     * Title: SmsSingleSender
     * </p>
     * Description:单条短信发送
     *
     * @param phoneNumber
     * @param content
     * @return
     */
    @Override
    public SmsSingleSenderResult send(String phoneNumber, String content) {
        SmsSingleSenderResult singleSenderResult = null;
        try {
            SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
            // 普通单发
            singleSenderResult = singleSender.send(0, "86", phoneNumber, content, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return singleSenderResult;
    }
}
