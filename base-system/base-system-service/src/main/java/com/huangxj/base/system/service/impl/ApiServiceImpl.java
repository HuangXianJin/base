package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.huangxj.common.core.constant.AppConstant;
import com.huangxj.common.core.exception.BaseException;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.common.core.utils.ReflectionUtils;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.entity.Api;
import com.huangxj.base.system.enums.AuthorityType;
import com.huangxj.base.system.mapper.ApiMapper;
import com.huangxj.base.system.service.ApiService;
import com.huangxj.base.system.service.AuthorityService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 系统资源-API接口 服务实现类
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ApiServiceImpl extends BaseServiceImpl<ApiMapper, Api> implements ApiService {

    @Autowired
    AuthorityService authorityService;

    @Autowired
    ConfigurableApplicationContext applicationContext;

    /**
     * 添加接口
     *
     * @param api
     * @return
     */
    @Override
    public void saveApi(Api api) {
        if (null != getApiByApiCode(api.getApiCode())) {
            throw new BaseException(String.format("%s编码已存在!", api.getApiCode()));
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        if (api.getStatus() == null) {
            api.setStatus(AppConstant.ENABLED);
        }
        if (api.getApiCategory() == null) {
            api.setApiCategory(AppConstant.DEFAULT_API_CATEGORY);
        }
        if (api.getIsPersist() == null) {
            api.setIsPersist(0);
        }
        if (api.getIsAuth() == null) {
            api.setIsAuth(1);
        }
        api.setCreateTime(new Date());
        baseMapper.insert(api);
        // 同步权限表里的信息
        authorityService.saveOrUpdateAuthority(api.getId(), AuthorityType.API.getCode());
    }

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    @Override
    public void updateApi(Api api) {
        Api saved = getById(api.getId());
        if (saved == null) {
            throw new BaseException("信息不存在!");
        }
        if (!saved.getApiCode().equals(api.getApiCode())) {
            // 和原来不一致重新检查唯一性
            if (null != getApiByApiCode(api.getApiCode())) {
                throw new BaseException(String.format("%s编码已存在!", api.getApiCode()));
            }
        }
        api.setModifyTime(new Date());
        baseMapper.updateById(api);
        // 同步权限表里的信息
        authorityService.saveOrUpdateAuthority(api.getId(), AuthorityType.API.getCode());
    }

    @Override
    public Api getApiByApiCode(String apiCode) {
        QueryWrapper<Api> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Api::getApiCode, apiCode);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    @Override
    public void removeApi(Integer apiId) {
        Api api = getById(apiId);
        if (api != null && api.getIsPersist().equals(AppConstant.ENABLED)) {
            throw new BaseException(String.format("保留数据,不允许删除"));
        }
        authorityService.removeAuthority(apiId, AuthorityType.API.getCode());
        baseMapper.deleteById(apiId);
    }


    /**
     * 清理无效数据
     *
     * @param serviceId
     * @param codes
     */
    @Override
    public void clearInvalidApi(String serviceId, Collection<String> codes) {
        if (StringUtils.isBlank(serviceId) || codes == null) {
            return;
        }
        List<String> invalidApiIds = listObjs(new QueryWrapper<Api>().lambda()
                        .select(Api::getId)
                        .eq(Api::getServiceId, serviceId)
                        .eq(Api::getIsPersist, 0)
                        .isNotNull(Api::getMethodName)
                        .notIn(Api::getApiCode, codes)
                , e -> e.toString());
        if (invalidApiIds != null) {
            // 获取无效的权限
            if (invalidApiIds.isEmpty()) {
                return;
            }
            // 移除接口资源
            invalidApiIds.forEach(id -> {
                this.removeApi(Integer.valueOf(id));
            });
        }
    }

    /**
     * @param apis
     */
    @Override
    public void updateAnnotationScanApi(List<Api> apis) {
        List<String> codes = Lists.newArrayList();
        for (Api api : apis) {
            try {
                codes.add(api.getApiCode());
                Api save = getApiByApiCode(api.getApiCode());
                if (save == null) {
                    api.setIsOpen(1);
                    saveApi(api);
                } else {
                    api.setIsPersist(save.getIsPersist());
                    api.setIsAuth(save.getIsAuth());
                    api.setIsOpen(save.getIsOpen());
                    save = BeanConverter.convertIgnoreNull(api, save);
                    updateApi(save);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("添加资源error:", e.getMessage());
            }
        }
    }

    private static final AntPathMatcher pathMatch = new AntPathMatcher();

    @Override
    public List<Api> scanApi() {
        List<Api> list = new ArrayList<>();
        Map<String, Object> resourceServer = applicationContext.getBeansWithAnnotation(EnableResourceServer.class);
        if (resourceServer == null || resourceServer.isEmpty()) {
            // 只扫描资源服务器
            return list;
        }
        Environment env = applicationContext.getEnvironment();
        // 服务名称
        String serviceId = env.getProperty("spring.application.name", "application");
        // 所有接口映射
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<RequestMatcher> permitAll = Lists.newArrayList();
        try {
            // 获取所有安全配置适配器
            Map<String, WebSecurityConfigurerAdapter> securityConfigurerAdapterMap = applicationContext.getBeansOfType(WebSecurityConfigurerAdapter.class);
            Iterator<Map.Entry<String, WebSecurityConfigurerAdapter>> iterable = securityConfigurerAdapterMap.entrySet().iterator();
            while (iterable.hasNext()) {
                WebSecurityConfigurerAdapter configurer = iterable.next().getValue();
                HttpSecurity httpSecurity = (HttpSecurity) ReflectionUtils.getFieldValue(configurer, "http");
                FilterSecurityInterceptor filterSecurityInterceptor = httpSecurity.getSharedObject(FilterSecurityInterceptor.class);
                FilterInvocationSecurityMetadataSource metadataSource = filterSecurityInterceptor.getSecurityMetadataSource();
                Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = (Map) ReflectionUtils.getFieldValue(metadataSource, "requestMap");
                Iterator<Map.Entry<RequestMatcher, Collection<ConfigAttribute>>> requestIterable = requestMap.entrySet().iterator();
                while (requestIterable.hasNext()) {
                    Map.Entry<RequestMatcher, Collection<ConfigAttribute>> match = requestIterable.next();
                    if (match.getValue().toString().contains("permitAll")) {
                        permitAll.add(match.getKey());
                    }
                }
            }
        } catch (Exception e) {
            log.error("error:{}", e);
        }
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            if (method.getMethod().getDeclaringClass().getAnnotation(RestController.class) == null) {
                // 只扫描RestController
                continue;
            }
            if (method.getMethodAnnotation(ApiIgnore.class) != null) {
                // 忽略的接口不扫描
                continue;
            }
            Set<MediaType> mediaTypeSet = info.getProducesCondition().getProducibleMediaTypes();
            for (MethodParameter params : method.getMethodParameters()) {
                if (params.hasParameterAnnotation(RequestBody.class)) {
                    mediaTypeSet.add(MediaType.APPLICATION_JSON_UTF8);
                    break;
                }
            }
            String mediaTypes = getMediaTypes(mediaTypeSet);
            // 请求类型
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            String methods = getMethods(methodsCondition.getMethods());
            // 请求路径
            PatternsRequestCondition p = info.getPatternsCondition();
            String urls = getUrls(p.getPatterns());
            // 类名
            String className = method.getMethod().getDeclaringClass().getName();
            // 方法名
            String methodName = method.getMethod().getName();
            String fullName = className + "." + methodName;
            String fullPath = methods + ":" + serviceId + urls;
            String name = "";
            String desc = "";
            // 是否需要安全认证 默认:1-是 0-否
            Integer isAuth = 1;
            // 匹配项目中.permitAll()配置
            for (String url : p.getPatterns()) {
                for (RequestMatcher requestMatcher : permitAll) {
                    if (requestMatcher instanceof AntPathRequestMatcher) {
                        AntPathRequestMatcher pathRequestMatcher = (AntPathRequestMatcher) requestMatcher;
                        if (pathMatch.match(pathRequestMatcher.getPattern(), url)) {
                            isAuth = 0;
                        }
                    }
                }
            }
            io.swagger.annotations.Api apiTag = method.getMethod().getDeclaringClass().getAnnotation(io.swagger.annotations.Api.class);
            ApiOperation apiOperation = method.getMethodAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                name = apiOperation.value();
                desc = apiOperation.notes();
            }
            if (apiTag != null && StringUtils.isNotBlank(apiTag.value())) {
                name = apiTag.value() + "：" + name;
            }
            name = StringUtils.isBlank(name) ? fullName : name;
            Api apiObj = new Api();
            apiObj.setApiName(name);
            apiObj.setApiCode(fullName);
            apiObj.setApiDesc(desc);
            apiObj.setPath(urls);
            apiObj.setClassName(className);
            apiObj.setMethodName(methodName);
            apiObj.setRequestMethod(methods);
            apiObj.setServiceId(serviceId);
            apiObj.setContentType(mediaTypes);
            apiObj.setIsAuth(isAuth);
            list.add(apiObj);
        }
        return list;
    }

    private String getMediaTypes(Set<MediaType> mediaTypes) {
        StringBuilder sbf = new StringBuilder();
        for (MediaType mediaType : mediaTypes) {
            sbf.append(mediaType.toString()).append(",");
        }
        if (mediaTypes.size() > 0) {
            sbf.deleteCharAt(sbf.length() - 1);
        }
        return sbf.toString();
    }

    private String getMethods(Set<RequestMethod> requestMethods) {
        StringBuilder sbf = new StringBuilder();
        for (RequestMethod requestMethod : requestMethods) {
            sbf.append(requestMethod.toString()).append(",");
        }
        if (requestMethods.size() > 0) {
            sbf.deleteCharAt(sbf.length() - 1);
        }
        return sbf.toString();
    }

    private String getUrls(Set<String> urls) {
        StringBuilder sbf = new StringBuilder();
        for (String url : urls) {
            sbf.append(url).append(",");
        }
        if (urls.size() > 0) {
            sbf.deleteCharAt(sbf.length() - 1);
        }
        return sbf.toString();
    }
}
