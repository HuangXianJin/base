package com.huangxj.generator;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * @ClassName MpGenerator
 * @Description 代码生成工具，idea需要安装Lombok Plugin
 * @Author: huangxj
 * @Create: 2019-08-07 14:06
 * @Version V1.0
 **/
public class MpGenerator {

    public static String output = "output";

    public static String type = "mysql";
    public static String driverName = "com.mysql.cj.jdbc.Driver";
    public static String url = "jdbc:mysql://47.107.101.62:3306/base?useSSL=false&useUnicode=true&characterEncoding=utf-8";
    public static String username = "root";
    public static String password = "123456";
    public static String author = "huangxj";
    public static String parentPackage = "com.huangxj.base";
    public static String commonPackage = "com.huangxj.common.core";
    public static String moduleName = "system";
    public static String includeTables = "sys_file";
    public static String tablePrefix = "sys_";

    /**
     * <p>
     *
     * </p>
     */
    public static void main(String[] args) {
        GenerateConfig config = GenerateConfig.builder()
                .author(author)
                .dbType(DbType.MYSQL)
                .jdbcDriver(driverName)
                .jdbcUrl(url)
                .jdbcUserName(username)
                .jdbcPassword(password)
                .parentPackage(parentPackage)
                .moduleName(moduleName)
                .includeTables(includeTables.split(","))
                .tablePrefix(tablePrefix.split(","))
                .outputDir(output)
                .commonPackage(commonPackage)
                .build();
        GeneratorService.execute(config);
    }
}
