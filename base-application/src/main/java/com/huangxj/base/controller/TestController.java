package com.huangxj.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.security.AuthHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName ProviderController
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-07-24 16:40
 * @Version V1.0
 **/
//@RestController
//@AllArgsConstructor
//@Api(tags = {"测试接口"})
//@Slf4j
public class TestController {

    /**
     * 获取用户基础信息
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @GetMapping("/current/user")
    public Result getUserProfile() {
        return Result.success().data(AuthHelper.getUser());
    }


    @PostMapping("/test/device")
    public Result postDeviceAttributes(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        return Result.success();
    }

}
