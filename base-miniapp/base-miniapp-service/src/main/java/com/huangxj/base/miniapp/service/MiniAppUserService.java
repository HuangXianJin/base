package com.huangxj.base.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.huangxj.common.core.exception.AlertException;
import com.huangxj.base.miniapp.config.WxMaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName MiniAppUserService
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-20 14:33
 * @Version V1.0
 **/
@Service("miniAppUserService")
@Slf4j
public class MiniAppUserService {

    public String getUserPhoneByCode(String appId, String code, String sessionKey, String encryptedData, String iv) {
        try {
            WxMaService wxService = WxMaConfiguration.getMaService(appId);
            if (StringUtils.isNotEmpty(code)) {
                sessionKey = wxService.getUserService().getSessionInfo(code).getSessionKey();
            }
            WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
            return phoneNoInfo.getPurePhoneNumber();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AlertException("登录失败");
        }
    }
}
