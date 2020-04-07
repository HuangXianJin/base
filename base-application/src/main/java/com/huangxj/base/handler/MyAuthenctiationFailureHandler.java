package com.huangxj.base.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangxj.common.core.exception.GlobalExceptionHandler;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huangxj
 * @description 当用户失败之后做的处理
 * @date 2019/1/8 0008 10:06
 */
@Component
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;


    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        Result result = GlobalExceptionHandler.resolveException(exception,request.getRequestURI());
        response.setStatus(result.getHttpStatus());
        WebUtils.writeJson(response, result);
    }
}
