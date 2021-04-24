package com.cxylk.classpath;

import java.nio.file.*;

/**
 * @Classname ZipEntry
 * @Description Zip或JAR文件形式的类路径
 * @Author likui
 * @Date 2021/4/23 10:53
 **/
public class ZipEntry implements Entry{
    /**
     * 绝对路径
     */
    private Path absPath;

    public ZipEntry(String path){
        absPath= Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        try(FileSystem zipFs=FileSystems.newFileSystem(absPath,null)){
//            System.out.println(zipFs.toString());
            //获取ZIP压缩包里的文件
            return Files.readAllBytes(zipFs.getPath(className));
        }
    }
}
