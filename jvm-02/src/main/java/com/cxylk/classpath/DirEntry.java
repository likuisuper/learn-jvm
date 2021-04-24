package com.cxylk.classpath;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Classname DicEntry
 * @Description 目录形式的类路径
 * @Author likui
 * @Date 2021/4/23 10:37
 **/
public class DirEntry implements Entry{

    /**
     * 绝对路径
     */
    private Path absPath;

    public DirEntry(String path){
        absPath=Paths.get(path).toAbsolutePath();
    }

    /**
     * 读取绝对路径下的class文件
     * @param className 绝对路径/ClassName.class
     * @return
     * @throws Exception
     */
    @Override
    public byte[] readClass(String className) throws Exception {
        return Files.readAllBytes(absPath.resolve(className));
    }
}
