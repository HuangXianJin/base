package com.huangxj.base.system.service;

import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.entity.Api;

import java.util.Collection;
import java.util.List;

/**
 * 系统资源-API接口 服务类
 *
 * @author huangxj
 * @date 2019-08-19
 */
public interface ApiService extends BaseService<Api> {

    /**
     * 新增接口
     *
     * @param api
     * @return
     */
    void saveApi(Api api);

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    void updateApi(Api api);

    /**
     * 获取api
     *
     * @param apiCode
     * @return
     */
    Api getApiByApiCode(String apiCode);

    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    void removeApi(Integer apiId);

    /**
     * 清理无效数据
     *
     * @param serviceId
     * @param codes
     */
    void clearInvalidApi(String serviceId, Collection<String> codes);

    /**
     * 更新注解的api
     *
     * @param apis
     */
    void updateAnnotationScanApi(List<Api> apis);

    /**
     * 扫描api
     *
     */
    List<Api> scanApi();
}
