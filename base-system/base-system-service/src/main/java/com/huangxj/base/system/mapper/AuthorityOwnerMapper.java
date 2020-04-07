package com.huangxj.base.system.mapper;

import com.huangxj.base.system.entity.AuthorityOwner;
import com.huangxj.common.core.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 系统权限-角色关联 Mapper 接口
 * @author huangxj
 * @date 2019-08-19
 */
@Mapper
public interface AuthorityOwnerMapper extends MyBaseMapper<AuthorityOwner> {

}
