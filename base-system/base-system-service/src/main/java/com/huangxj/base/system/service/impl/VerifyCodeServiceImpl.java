package com.huangxj.base.system.service.impl;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.huangxj.common.core.exception.AlertException;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.common.core.utils.RandomValueUtils;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.entity.VerifyCode;
import com.huangxj.base.system.enums.DataStatus;
import com.huangxj.base.system.mapper.VerifyCodeMapper;
import com.huangxj.base.system.service.SmsService;
import com.huangxj.base.system.service.VerifyCodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * 验证码表 服务实现类
 *
 * @author yuguohui
 * @date 2020-03-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class VerifyCodeServiceImpl extends BaseServiceImpl<VerifyCodeMapper, VerifyCode> implements VerifyCodeService {

    SmsService smsService;

    @Override
    public void sendCode(String phone) {
        if (!StringUtils.isPhoneNo(phone)) {
            throw new AlertException("手机号为空或格式不正确！");
        }

        VerifyCode verifyCode = this.baseMapper.getCodeByPhone(phone);

        if (null != verifyCode && (System.currentTimeMillis() - verifyCode.getCreateTime().getTime()) < 30000) {
            throw new AlertException("请勿频繁操作！");
        }
        String code = RandomValueUtils.createVerifyCode();
        SmsSingleSenderResult senderResult = smsService.send(phone,
                code + "为您的验证码，请于5分钟内填写。如非本人操作，请忽略本短信。");
        if (senderResult.result == 0) {
            VerifyCode shortMessage = new VerifyCode();
            shortMessage.setPhone(phone);
            shortMessage.setVerifyCode(code);
            shortMessage.setStatus(DataStatus.VALID.getCode());
            shortMessage.setCreateTime(new Date());
            save(shortMessage);
        } else {
            throw new AlertException("验证码发送失败");
        }
    }

    @Override
    public void verify(String phone, String code) {
        VerifyCode verifyCode = this.baseMapper.getCodeByPhone(phone);
        if (null == verifyCode) {
            throw new AlertException("请获取验证码");
        }
        if (verifyCode.getStatus() == DataStatus.INVALID.getCode()) {
            throw new AlertException("验证码已失效，请重新获取");
        }
        // 超过5分钟 已失效
        if ((System.currentTimeMillis() - verifyCode.getCreateTime().getTime()) > 300000) {
            throw new AlertException("验证码已过期，请重新获取");
        }
        if (!Objects.equals(code, verifyCode.getVerifyCode())) {
            throw new AlertException("验证码错误");
        }
    }

    @Override
    public void invalidCode(String phone) {
        VerifyCode verifyCode = this.baseMapper.getCodeByPhone(phone);
        verifyCode.setModifyTime(new Date());
        verifyCode.setStatus(DataStatus.INVALID.getCode());
        this.updateById(verifyCode);
    }

    @Override
    public String checkCode(String phone) {
        VerifyCode verifyCode = this.baseMapper.getCodeByPhone(phone);
        if (null == verifyCode) {
            return "";
        }
        if (verifyCode.getStatus() == DataStatus.INVALID.getCode()) {
            return "";
        }
        // 超过5分钟 已失效
        if ((System.currentTimeMillis() - verifyCode.getCreateTime().getTime()) > 300000) {
            return "";
        }
        verifyCode.setModifyTime(new Date());
        verifyCode.setStatus(DataStatus.INVALID.getCode());
        this.updateById(verifyCode);
        return verifyCode.getVerifyCode();
    }
}

