package cn.org.easysite.generator.tool;

import cn.org.easysite.generator.mybatis.tool.GeneratorTools;

import java.io.File;

/**
 * Hello world!
 *
 */
public class Tool {

    public static void main(String[] args) throws Exception {
        GeneratorTools.generator(new File(Tool.class.getClassLoader().getResource("generator/generatorConfig.xml").getFile()));
    }
}
