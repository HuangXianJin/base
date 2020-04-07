package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.Api;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统资源-API接口数据传输对象实体类
 *
 * @author huangxj
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiDto extends Api implements Serializable, Comparable<ApiDto> {

    private Integer authorityId;

    private String authority;

    @Override
    public int compareTo(ApiDto o) {
        return this.getPath().compareTo(o.getPath());
    }
}
