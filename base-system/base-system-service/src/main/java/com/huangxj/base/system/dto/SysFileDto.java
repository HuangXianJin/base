package com.huangxj.base.system.dto;

import com.huangxj.base.system.entity.SysFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 文件资源管理数据传输对象实体类
 *
 * @author huangxj
 * @since 2020-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFileDto extends SysFile implements Serializable {
    private String fileUrl;

    private byte[] buffer;


}
