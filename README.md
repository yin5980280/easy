# Spring-boot 开发脚手架

## 项目介绍
提供快速的搭建spring,spring boot项目,提供spring boot阔展等

## 功能介绍
集成tk-mybatis,并扩展了BaseService 和 LogicService(带逻辑删除)

## 模块介绍

### easy-dependencies
该模块管理所有的jar包集成，整合spring boot和spring cloud等

### easy-parent
该模块主要为其他模块和开发应用模块提供一个parent模块，使用类似于spring-boot-parent,在pom.xml中加入如下配置
```
  <parent>
        <artifactId>easy-parent</artifactId>
        <groupId>com.vartime.easy</groupId>
        <version>${VERSION}</version>
   </parent>
```

### easy-common
该模块是easy的一个基础模块，提供lombok，基础object和utils等工具

### easy-framework
该模块是easy的核心模块，提供spring的扩展等

### easy-spring-boot
该模块是对spring-boot的扩展，提供统一返回JSON格式，自定义过滤规则和swagger-ui-bootstrap等

### easy-spring-boot-tk-mybatis
该模块是对mapper-spring-boot-starter的一个扩展，新增了对基础LogicEntity实体，commonMapper，BaseService,BaseLogicService等实现
