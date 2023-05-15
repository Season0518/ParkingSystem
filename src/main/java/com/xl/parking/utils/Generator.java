package com.xl.parking.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @author XL
 * 代码构造器
 * @date 2021/12/10 8:45
 */
public class Generator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //1. 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //是否支持AR模式
//        gc.setActiveRecord(true);
        //生成路径
        gc.setOutputDir(projectPath + "/src/main/java");
        //作者
//        gc.setAuthor("XL");
        gc.setOpen(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(false);
        //文件覆盖
        gc.setFileOverride(true);
        //主键策略
        gc.setIdType(IdType.AUTO);
        //设置生成的service接口的名字的首字母是否为I
        gc.setServiceName("%sService");

        gc.setBaseResultMap(true);

        gc.setBaseColumnList(true);
        //整合配置
        mpg.setGlobalConfig(gc);



        //2。 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();

        //设置数据库类型
        // dsc.setDbType(DbType.MYSQL);

        dsc.setUrl("jdbc:mysql://localhost:3306/parking?serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1234");
        //整合配置
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));

        pc.setParent("com.xl.parking")
           .setMapper("mapper")
           .setController("controller")
           .setService("service")
           .setEntity("domain")
           .setXml("mapper");
        //整合配置
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };


        // 3.策略配置
        StrategyConfig strategy = new StrategyConfig();

        //全局大写命名
        strategy.setCapitalMode(true);
        //数据库映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //设置表的前缀
//        strategy.setTablePrefix("tbl_");
        //生成的表
//        strategy.setInclude("yanlian");

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        //整合配置
        mpg.setStrategy(strategy);
        //执行
        mpg.execute();
    }
}
