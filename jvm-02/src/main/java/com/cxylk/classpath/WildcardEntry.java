package com.cxylk.classpath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname WildcardEntry
 * @Description 读取路径末尾带*号的路径。也就是匹配通配符*
 * @Author likui
 * @Date 2021/4/23 14:58
 **/
public class WildcardEntry extends CompositeEntry {

    public WildcardEntry(String pathList) {
        super(toPathList(pathList));
    }

    public static String toPathList(String wildcardPath) {
        //替换通配符*
        String baseDir = wildcardPath.replace("*", "");
        try (Stream<Path> pathStream = Files.walk(Paths.get(baseDir))) {
            return pathStream.filter(Files::isRegularFile)//筛选出文件
                    .map(Path::toString)//转换成string
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))//筛选出jar（JAR）包
                    .collect(Collectors.joining(File.pathSeparator));
        } catch (IOException e) {
            return "";
        }
    }
}
