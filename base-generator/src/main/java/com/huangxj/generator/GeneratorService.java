package com.huangxj.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 */
public class GeneratorService {

    public static void execute(GenerateConfig generateConfig) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(generateConfig.getOutputDir());
        gc.setFileOverride(true);
        //ActiveRecord特性
        gc.setActiveRecord(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setEnableCache(false);
        // 自动打开输出目录
        gc.setOpen(false);
        gc.setAuthor(generateConfig.getAuthor());
        gc.setSwagger2(true);
        //主键策略
        gc.setIdType(IdType.ID_WORKER);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(generateConfig.getDbType());
        dsc.setDriverName(generateConfig.getJdbcDriver());
        dsc.setUrl(generateConfig.getJdbcUrl());
        dsc.setUsername(generateConfig.getJdbcUserName());
        dsc.setPassword(generateConfig.getJdbcPassword());
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                //将数据库中datetime转换成date
                if (fieldType.toLowerCase().contains("datetime")) {
                    return DbColumnType.DATE;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }
        });
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(false);
        strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);
        strategy.setEntityTableFieldAnnotationEnable(false);
        // 此处可以移除表前缀表前缀
        strategy.setTablePrefix(generateConfig.getTablePrefix());

        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 字段生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityColumns("create_time", "update_time");
        // mapper 父类
        strategy.setSuperMapperClass(generateConfig.getCommonPackage()+".mapper.MyBaseMapper");
//        // 实体父类
//        strategy.setSuperEntityClass("com.opencloud.common.mybatis.base.entity.AbstractEntity");
        // 接口父类
        strategy.setSuperServiceClass(generateConfig.getCommonPackage()+".service.BaseService");
        // 接口实现类父类
        strategy.setSuperServiceImplClass(generateConfig.getCommonPackage()+".service.impl.BaseServiceImpl");
        // 需要生成的表
        strategy.setInclude(generateConfig.getIncludeTables());
        ConfigBuilder config = new ConfigBuilder(new PackageConfig(), dsc, strategy, new TemplateConfig(), gc);
        List<TableInfo> list = config.getTableInfoList();

        // 公共字段填充
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(new TableFill("create_time", FieldFill.INSERT));
        tableFills.add(new TableFill("create_id", FieldFill.INSERT));
        tableFills.add(new TableFill("modify_id", FieldFill.UPDATE));
        tableFills.add(new TableFill("modify_time", FieldFill.UPDATE));
        strategy.setTableFillList(tableFills);
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        //父包名
        pc.setParent(generateConfig.getParentPackage());
        //父包模块名
        pc.setModuleName(generateConfig.getModuleName());
        //实体类父包
        pc.setEntity("entity");
        //controller父包
        pc.setController("controller");
        //mapper父包
        pc.setMapper("mapper");
        //xml父包
        pc.setXml("xml");
        pc.setServiceImpl("service.impl");
        pc.setService("service");
        Map map = new HashMap();
        map.put("commonPackage",generateConfig.getCommonPackage());
        map.put("parentPackage",generateConfig.getParentPackage()+"."+generateConfig.getModuleName());
        map.put("moduleName",generateConfig.getModuleName());
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        cfg.setMap(map);
        cfg.initMap();
        String jsPath = "/templates/api.js.vm";
        String vuePath = "/templates/index.vue.vm";
        List<FileOutConfig> focList = new ArrayList<>();


        focList.add(new FileOutConfig("/templates/entityDto.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = gc.getOutputDir() + File.separator + pc.getParent().replace(".", File.separator) + File.separator + "dto" + File.separator + tableInfo.getEntityName() + "Dto.java";
                return path;
            }
        });

        focList.add(new FileOutConfig("/templates/entityVo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = gc.getOutputDir() + File.separator + pc.getParent().replace(".", File.separator) + File.separator + "vo" + File.separator + tableInfo.getEntityName() + "Vo.java";
                return path;
            }
        });


        focList.add(new FileOutConfig("/templates/entityParam.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = gc.getOutputDir() + File.separator + pc.getParent().replace(".", File.separator) + File.separator + "param" + File.separator + tableInfo.getEntityName() + "Param.java";
                return path;
            }
        });

        focList.add(new FileOutConfig("/templates/wrapper.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = gc.getOutputDir() + File.separator + pc.getParent().replace(".", File.separator) + File.separator + "wrapper" + File.separator + tableInfo.getEntityName() + "Wrapper.java";
                return path;
            }
        });

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(jsPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = gc.getOutputDir() + File.separator + pc.getParent().replace(".", File.separator) + File.separator + "js" + File.separator + tableInfo.getName() + ".js";
                return path;
            }
        });
        focList.add(new FileOutConfig(vuePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = gc.getOutputDir() + File.separator + pc.getParent().replace(".", File.separator) + File.separator + "view" + File.separator + tableInfo.getName() + File.separator + "index.vue";
                return path;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setPackageInfo(pc);
        // 执行生成
        mpg.execute();
    }
}
