package com.huangxj.base.system.service.impl;


import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.base.system.entity.Log;
import com.huangxj.base.system.mapper.LogMapper;
import com.huangxj.base.system.service.LogService;
import org.springframework.stereotype.Service;

/**
 * 日志管理 服务实现类
 * @ClassName LogServiceImpl
 * @Author: huangxj
 *
 * @Create: 2018/11/26
 **/
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements LogService {

}
