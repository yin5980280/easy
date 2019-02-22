package com.vartime.easy.generator.mybatis.service.plugin;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ServicePlugin extends PluginAdapter {

    private String baseService;
    private String baseServiceImpl;
    private String targetProject;
    private String targetPackage;

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String baseService = this.properties.getProperty("baseService");
        if (StringUtility.stringHasValue(baseService)) {
            this.baseService = baseService;
        } else {
            throw new RuntimeException("baseService 属性不能为空！");
        }
        String baseServiceImpl = this.properties.getProperty("baseServiceImpl");
        if (StringUtility.stringHasValue(baseServiceImpl)) {
            this.baseServiceImpl = baseServiceImpl;
        } else {
            throw new RuntimeException("baseServiceImpl 属性不能为空！");
        }
        String targetProject = this.properties.getProperty("targetProject");
        if (StringUtility.stringHasValue(targetProject)) {
            this.targetProject = targetProject;
        } else {
            throw new RuntimeException("targetProject 属性不能为空！");
        }
        String targetPackage = this.properties.getProperty("targetPackage");
        if (StringUtility.stringHasValue(targetPackage)) {
            this.targetPackage = targetPackage;
        } else {
            throw new RuntimeException("targetPackage 属性不能为空！");
        }
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable table) {
        return Arrays.asList(generateService(table), generateServiceImpl(table));
    }

    private GeneratedJavaFile generateService(IntrospectedTable table) {
        // 获取实体类型
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // 生成 Service 名称
        String service = targetPackage + "." + table.getFullyQualifiedTable().getDomainObjectName() + "Service";
        // 构造 Service 文件
        Interface interfaze = new Interface(new FullyQualifiedJavaType(service));
        // 设置作用域
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        // import
        interfaze.addImportedType(entityType);
        interfaze.addImportedType(new FullyQualifiedJavaType(baseService));
        interfaze.addSuperInterface(new FullyQualifiedJavaType(
                getClassName(baseService) + "<" + entityType.getShortName() + ">"));
        return new GeneratedJavaFile(interfaze, targetProject, new DefaultJavaFormatter());
    }

    private GeneratedJavaFile generateServiceImpl(IntrospectedTable table) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        String domainObjectName = table.getFullyQualifiedTable().getDomainObjectName();
        String service = targetPackage + "." + domainObjectName + "Service";
        String serviceImpl = targetPackage + ".impl." + domainObjectName + "ServiceImpl";
        TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(serviceImpl));
        clazz.addImportedType(entityType);
        clazz.addImportedType(new FullyQualifiedJavaType(service));
        clazz.addImportedType(new FullyQualifiedJavaType(baseServiceImpl));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        clazz.addAnnotation("@Service(\"" + firstLetterLowerCase(domainObjectName + "Service") + "\")");
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.setSuperClass(new FullyQualifiedJavaType(
                getClassName(baseServiceImpl) + "<" + entityType.getShortName() + ">"));
        clazz.addSuperInterface(new FullyQualifiedJavaType(service));
        return new GeneratedJavaFile(clazz, targetProject, new DefaultJavaFormatter());
    }

    private String firstLetterLowerCase(String name) {
        char c = name.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            String temp = String.valueOf(c);
            return name.replaceFirst(temp, temp.toLowerCase());
        }
        return name;
    }

    private String getClassName(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

}
