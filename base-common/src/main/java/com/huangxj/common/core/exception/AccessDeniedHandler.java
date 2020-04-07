package com.huangxj.common.core.exception;

import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问拒绝
 * @author liuyadu
 */
@Slf4j
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException, ServletException {
        Result result = GlobalExceptionHandler.resolveException(exception,request.getRequestURI());
        response.setStatus(result.getHttpStatus());
        WebUtils.writeJson(response, result);
    }
}
