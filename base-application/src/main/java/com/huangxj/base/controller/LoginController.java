package com.huangxj.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.huangxj.common.core.exception.BaseException;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.security.AuthHelper;
import com.huangxj.common.core.security.AuthUserDetails;
import com.huangxj.base.security.UserServiceDetail;
import com.huangxj.base.system.api.AppServiceClient;
import com.huangxj.base.system.entity.App;
import com.huangxj.base.system.service.VerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-08-01 16:35
 * @Version V1.0
 **/
@Api(tags = "用户认证中心")
@RestController
@RequestMapping("uaa-auth")
public class LoginController {

    @Autowired
    AppServiceClient appService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${server.port}")
    String port;

    @Value("${clientId}")
    String clientId;

    @Autowired
    UserServiceDetail userServiceDetail;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        return "hello world  !!";
    }

    /**
     * 获取用户基础信息
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息并刷新权限", notes = "获取当前登录用户信息并刷新权限")
    @GetMapping("/current/user")
    public Result getUserProfile() {
        AuthUserDetails authUserDetails = AuthHelper.getUser();
        authUserDetails = (AuthUserDetails) userServiceDetail.loadUserByUsername(authUserDetails.getUsername());
        AuthHelper.updateAuthUser(tokenStore, authUserDetails);
        return Result.success().data(authUserDetails);
    }

    @ApiOperation("发送验证码")
    @PostMapping("sendVerifyCode")
    public Result sendVerifyCode(String phoneNumber) {
        try {
            verifyCodeService.sendCode(phoneNumber);
        } catch (BaseException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 获取用户访问令牌
     * 基于oauth2密码模式登录
     *
     * @param form
     * @return access_token
     */
    @ApiOperation(value = "登录获取用户访问令牌", notes = "基于oauth2密码模式登录,无需签名,返回access_token")
    @PostMapping("/login/password")
    public Object getLoginToken(@RequestBody PasswordForm form, @RequestHeader HttpHeaders headers) {
        JSONObject result = passwordAccessToken(form.getUsername(), form.getPassword(), headers);
        String accessToken = "access_token";
        if (result.containsKey(accessToken)) {
            return Result.success().data(result);
        } else {
            return result;
        }
    }

    /**
     * 获取客户端访问令牌
     * 基于oauth2客户端模式登录
     *
     * @param form
     * @return access_token
     */
    @ApiOperation(value = "获取客户端访问令牌", notes = "基于oauth2客户端模式登录,无需签名,返回access_token")
    @PostMapping("/login/client")
    public Result<OAuth2AccessToken> getClientToken(@RequestBody ClientForm form, @RequestHeader HttpHeaders headers) {
        OAuth2AccessToken result = clientCredentialsAccessToken(form.getClientId(), form.getSecret());
        return Result.success().data(result);
    }


    /**
     * 获取token,基于oauth2密码模式登录
     *
     * @param userName
     * @param password
     * @return
     */
    public JSONObject passwordAccessToken(String userName, String password, HttpHeaders headers) {
        App app = appService.getAppByClientId(clientId).getData();
        if (app == null) {
            throw new ClientRegistrationException("认证服务器禁止访问");
        }
        String url = "http://127.0.0.1:" + port + "/oauth/token";
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", userName);
        postParameters.add("password", password);
        postParameters.add("client_id", app.getApiKey());
        postParameters.add("client_secret", app.getSecretKey());
        postParameters.add("grant_type", "password");
        postParameters.add("scope", "listener");

        // 使用客户端的请求头,发起请求
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头,防止token失效
        headers.remove(HttpHeaders.AUTHORIZATION);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);
        JSONObject result = restTemplate.postForObject(url, request, JSONObject.class);
        return result;
    }


    /**
     * 基于oauth2客户端模式登录
     *
     * @param clientId
     * @param secret
     * @return
     */
    public OAuth2AccessToken clientCredentialsAccessToken(String clientId, String secret) {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setClientId(clientId);
        resource.setClientSecret(secret);
        resource.setAccessTokenUri("http://127.0.0.1:" + port + "/oauth/token");
        resource.setGrantType("client_credentials");
        resource.setScope(Arrays.asList("read", "write"));
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        AccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, atr);

        return accessToken;
    }

    @Data
    @AllArgsConstructor
    public static class PasswordForm {
        @ApiModelProperty(value = "用户名")
        private String username;
        @ApiModelProperty(value = "密码")
        private String password;
    }

    @Data
    public static class ClientForm {
        @ApiModelProperty(value = "客户端id")
        private String clientId;
        @ApiModelProperty(value = "客户端密钥")
        private String secret;
    }

}
