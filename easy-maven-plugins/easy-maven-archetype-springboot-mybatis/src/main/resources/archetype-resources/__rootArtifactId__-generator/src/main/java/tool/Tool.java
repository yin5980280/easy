package ${package}.tool;

import java.io.File;

import cn.org.easysite.generator.mybatis.tool.GeneratorTools;

/**
 * Hello world!
 *
 */
public class Tool {

    public static void main(String[] args) throws Exception {
        GeneratorTools.generator(new File(Tool.class.getClassLoader().getResource("generator/generatorConfig.xml").getFile()));
    }
}
