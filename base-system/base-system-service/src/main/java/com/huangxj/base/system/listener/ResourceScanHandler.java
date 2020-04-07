package com.huangxj.base.system.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.entity.Api;
import com.huangxj.base.system.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * mq消息接收者
 *
 * @author liuyadu
 */
@Slf4j
@Component
public class ResourceScanHandler {

    @Autowired
    ApiService apiService;


    /**
     * 接收API资源扫描消息
     */
    public void handler(JSONObject resource) {
        try {
            String serviceId = resource.getString("application");
            JSONArray array = resource.getJSONArray("data");
            Iterator iterator = array.iterator();
            List<String> codes = Lists.newArrayList();
            while (iterator.hasNext()) {
                try {
                    JSONObject jsonObject = (JSONObject) iterator.next();
                    Api api = jsonObject.toJavaObject(Api.class);
                    codes.add(api.getApiCode());
                    Api save = apiService.getApiByApiCode(api.getApiCode());
                    if (save == null) {
                        api.setIsOpen(1);
                        api.setIsPersist(1);
                        apiService.saveApi(api);
                    } else {
                        save = BeanConverter.convertIgnoreNull(api, save);
                        apiService.updateApi(save);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("添加资源error:", e.getMessage());
                }

            }
            if (array != null && array.size() > 0) {
                // 清理无效权限数据
            }

        } catch (Exception e) {
            log.error("error:", e);
        }
    }

}
