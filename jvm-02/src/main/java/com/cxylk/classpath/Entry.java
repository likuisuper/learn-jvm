package com.cxylk.classpath;

import java.io.File;

/**
 * @Classname Entry
 * @Description 类路径接口
 * @Author likui
 * @Date 2021/4/23 10:33
 **/
public interface Entry {
    /**
     * 从给定的class文件中读取字节流
     * @param className com/cxylk/ClassName.class
     * @return
     * @throws Exception
     */
    byte[] readClass(String className) throws Exception;

    /**
     * 工厂模式
     * @param path
     * @return
     */
    static Entry create(String path){
        //File.pathSeparator->;(分号)
        if(path.contains(File.pathSeparator)){
            return new CompositeEntry(path);
        }else if(path.endsWith(".jar")||path.endsWith(".ZIP")){
            return new ZipEntry(path);
        }else if(path.endsWith("*")){
            return new WildcardEntry(path);
        } else {
            return new DirEntry(path);
        }
    }
}
