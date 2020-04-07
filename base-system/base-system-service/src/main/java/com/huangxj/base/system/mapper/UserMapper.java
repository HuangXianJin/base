package com.huangxj.base.system.mapper;

import com.huangxj.base.system.entity.User;
import com.huangxj.common.core.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 用户信息 Mapper 接口
 * @author huangxj
 * @date 2019-08-08
 */
@Mapper
public interface UserMapper extends MyBaseMapper<User> {

}
