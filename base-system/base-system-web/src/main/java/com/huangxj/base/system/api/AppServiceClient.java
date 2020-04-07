package com.huangxj.base.system.api;

import com.huangxj.common.core.model.Result;
import com.huangxj.base.system.entity.App;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 客户端应用 com.huangxj.base.system.listener
 *
 * @author huangxj
 * @since 2019-08-08
 */
public interface AppServiceClient {

    /**
     * 获取客户端信息
     *
     * @param clientId
     * @return
     */
    @GetMapping("/app/clientId/{clientId}")
    Result<App> getAppByClientId(@PathVariable("clientId") String clientId);

    /**
     * 获取应用服务器应用详情
     *
     * @param
     * @return
     */
    @GetMapping("/app/serverApp")
    Result<App> getServerApp();
}
