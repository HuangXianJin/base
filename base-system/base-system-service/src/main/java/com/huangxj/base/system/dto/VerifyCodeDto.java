package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.VerifyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 验证码表数据传输对象实体类
 *
 * @author yuguohui
 * @since 2020-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VerifyCodeDto extends VerifyCode implements Serializable {

}
