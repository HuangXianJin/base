package com.huangxj.base.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangxj.common.core.enums.LoginType;
import com.huangxj.common.core.security.LoginTypeContext;
import com.huangxj.common.core.utils.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName CustomAuthenticationFilter
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-04 16:24
 * @Version V1.0
 **/
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                Map<String, String> authenticationBean = mapper.readValue(is, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.get("username"), authenticationBean.get("password"));

                if (StringUtils.equals(authenticationBean.get("loginType"), LoginType.CODE.getCode())) {
                    LoginTypeContext.set(LoginType.CODE.getCode());
                }

//                if (StringUtils.equals(authenticationBean.get("loginType"), LoginType.MINIAPP.getCode())) {
//                    LoginTypeContext.set(LoginType.MINIAPP.getCode());
//                    MiniAppUserService miniAppUserService = SpringContextHolder.getBean(MiniAppUserService.class);
//                    String appId = authenticationBean.get("appId");
//                    String code = authenticationBean.get("code");
//                    String sessionKey = authenticationBean.get("sessionKey");
//                    String encryptedData = authenticationBean.get("encrypted_data");
//                    String iv = authenticationBean.get("iv");
//                    String phone = miniAppUserService.getUserPhoneByCode(appId, code, sessionKey, encryptedData, iv);
//                    authRequest = new UsernamePasswordAuthenticationToken(phone, phone);
//                }

            } catch (Exception e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
