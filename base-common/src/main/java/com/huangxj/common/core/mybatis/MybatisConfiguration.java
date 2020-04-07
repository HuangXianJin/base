package com.huangxj.common.core.mybatis;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.google.common.collect.Sets;
import com.huangxj.common.core.security.AuthHelper;
import com.huangxj.common.core.security.AuthUserDetails;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName MybatisConfiguration
 * @Author: huangxj
 * @Create: 2019-08-14 15:29
 * @Version V1.0
 **/
@Slf4j
@Configuration
public class MybatisConfiguration {

    private Set<String> ignores = Sets.newHashSet("");

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean where) {
                AuthUserDetails user = AuthHelper.getUser();
                if (user != null) {
                    return new StringValue(user.getTenantCode());
                }
                return new StringValue("");
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_code";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                AuthUserDetails user = AuthHelper.getUser();
                if (user == null) {
                    return true;
                }
                // 这里可以判断是否过滤表
                String userTable = "sys_user";
                if (StringUtils.equals(tableName, userTable)) {
                    return false;
                }
                String prefix = "sys_";
                if (tableName.startsWith(prefix)) {
                    return true;
                }

                if (ignores.contains(tableName)) {
                    return true;
                }
                return true;
            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
//                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
//                // 过滤自定义查询此时无租户信息约束
//                if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
//                    return true;
//                }
                return false;
            }
        });
        return paginationInterceptor;
    }

}
