package com.vartime.easy.generator.tool;

import com.vartime.easy.generator.mybatis.tool.GeneratorTools;

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
