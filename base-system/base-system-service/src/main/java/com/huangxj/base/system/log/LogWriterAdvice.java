package com.huangxj.base.system.log;


import com.alibaba.fastjson.JSON;
import com.huangxj.common.core.security.AuthHelper;
import com.huangxj.base.system.entity.Log;
import com.huangxj.base.system.service.LogService;
import com.huangxj.base.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: LogWriterAdvice
 * @Description: TODO
 * @Author: huangxj
 * @Create: 2018/11/30
 * @Version: 1.0
 **/
@Aspect
@Component
public class LogWriterAdvice {

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String get = "GET";

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Pointcut("execution(* com.huangxj.base.*.controller..*.*(..)) "+
            "&& !execution(* com.huangxj.base.*.controller.UserController.getUserByUsername(..))"+
            "&& !execution(* com.huangxj.base.*.controller.AuthorityController.findAuthorityUser(..))"+
            "&& !execution(* com.huangxj.base.*.controller.AppController.getAppByClientId(..))"+
            "&& !execution(* com.huangxj.base.*.controller.AuthorityController.findAuthorityApp(..))"+
            "&& !execution(* com.huangxj.base.*.controller.UserController.getUserByUsername(..))"+
            "&& !execution(* com.huangxj.base.*.controller.AuthorityController.findAuthorityUser(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String multipart = "multipart";
        try {
            if (!get.equals(request.getMethod())) {
                Signature signature = joinPoint.getSignature();
                MethodSignature methodSignature = (MethodSignature) signature;
                Method method = methodSignature.getMethod();
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                Api api = method.getDeclaringClass().getAnnotation(Api.class);

                String contentType = request.getContentType();
//                int totalBytes =request.getContentLength();
                Log log = new Log();
                log.setUrl(request.getRequestURL().toString());
                log.setHttpMethod(request.getMethod());
                log.setIp(request.getRemoteAddr());
                log.setClassMethod(method.getDeclaringClass().getName() + "." + method.getName());
                if(null!=AuthHelper.getUser()){
                    log.setOperator(AuthHelper.getUser().getUsername());
                }
                if (null != api && null != apiOperation) {
                    log.setModule(api.tags()[0]);
                    log.setOperation(apiOperation.value());
                }
                log.setOperateTime(df.format(new Date()));
//                if(totalBytes<4096){
                    log.setArgs(JSON.toJSONString(joinPoint.getArgs()));
//                }
                log.setResponse(JSON.toJSONString(ret));
                logger.debug("操作日志:{}", log.toString());
                logService.save(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
