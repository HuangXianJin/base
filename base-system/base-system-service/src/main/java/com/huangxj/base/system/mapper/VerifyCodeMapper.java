package com.huangxj.base.system.mapper;

import com.huangxj.common.core.mapper.MyBaseMapper;
import com.huangxj.base.system.dto.VerifyCodeDto;
import com.huangxj.base.system.entity.VerifyCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 验证码表 Mapper 接口
 * @author yuguohui
 * @date 2020-03-01
 */
@Mapper
public interface VerifyCodeMapper extends MyBaseMapper<VerifyCode> {

    /**
     * 根据手机号查询验证码
     * @param phone 手机号
     * @return 验证码对象
     */
    VerifyCodeDto getCodeByPhone(@Param("phone") String phone);

}
