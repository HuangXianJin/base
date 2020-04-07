package com.huangxj.base.system.service.impl;

import com.huangxj.base.system.entity.Dictionary;
import com.huangxj.base.system.mapper.DictionaryMapper;
import com.huangxj.base.system.service.DictionaryService;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  服务实现类
 *
 * @author huangxj
 * @date 2019-10-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

}
