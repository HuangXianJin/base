package com.huangxj.base.system.mapper;

import com.huangxj.base.system.entity.SysFile;
import com.huangxj.common.core.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 文件资源管理 Mapper 接口
 * @author huangxj
 * @date 2020-02-20
 */
@Mapper
public interface FileMapper extends MyBaseMapper<SysFile> {

}
