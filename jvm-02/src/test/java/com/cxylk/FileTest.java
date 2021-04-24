package com.cxylk;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname FileTest
 * @Description 文件流的一些测试
 * @Author likui
 * @Date 2021/4/23 15:33
 **/
public class FileTest {

    @Test
    public void getDirectories(){
        //输出该目录下的所有目录
        String baseDir="D:\\github";
        try(Stream<Path> walk=Files.walk(Paths.get(baseDir))){
            walk.filter(Files::isDirectory)//筛选出目录
                    .map(Path::toString)//将Path转换为字符串
                    .collect(Collectors.toList())//将结果转换为list
                    .forEach(System.out::println);//遍历
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFiles(){
        //输出该目录下的所有文件，包括目录中的文件
        String baseDir="D:\\我的文档\\Java";
        try(Stream<Path> walk=Files.walk(Paths.get(baseDir))){
            String result = walk.filter(Files::isRegularFile)//筛选出文件
                    .map(Path::toString)//将Path转换为字符串
                    .filter(p -> p.endsWith("io"))//筛选出后缀是io的文件
                    .collect(Collectors.joining(File.pathSeparator));//将结果使用;连接
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
