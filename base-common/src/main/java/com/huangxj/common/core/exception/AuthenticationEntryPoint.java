package com.huangxj.common.core.exception;

import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未认证处理
 *
 * @author liuyadu
 */
@Slf4j
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException, ServletException {
        Result result = GlobalExceptionHandler.resolveException(exception,request.getRequestURI());
        response.setStatus(result.getHttpStatus());
        WebUtils.writeJson(response, result);
    }
}