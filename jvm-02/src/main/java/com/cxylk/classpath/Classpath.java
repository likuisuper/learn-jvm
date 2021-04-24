package com.cxylk.classpath;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Classname Classpath
 * @Description 不同路径下的class文件读取
 * @Author likui
 * @Date 2021/4/23 16:18
 **/
public class Classpath {
    //启动类路径
    private Entry bootClasspath;

    //扩展类路径
    private Entry extClasspath;

    //用户类路径
    private Entry userClasspath;

    public Classpath(String jreOption, String cpOption) {
        //启动类&扩展类 jdk/jre/lib
        bootstrapAndExtClasspath(jreOption);
        //用户类 D:../Hello
        parseUserClasspath(cpOption);
    }

    /**
     * 解析启动类路径&扩展类路径
     *
     * @param jreOption
     */
    private void bootstrapAndExtClasspath(String jreOption) {
        String jreDir = parseJreDir(jreOption);
        // jre/lib/*
        String libPath = Paths.get(jreDir, "lib") + File.separator + "*";
        //确定带有通配符，直接使用WildcardEntry类
        bootClasspath = new WildcardEntry(libPath);
        // jre/lib/ext/*
        String extPath = Paths.get(jreDir, "lib", "ext") + File.separator + "*";
        extClasspath = new WildcardEntry(extPath);
    }

    /**
     * 解析用户类路径
     *
     * @param cpOption
     */
    private void parseUserClasspath(String cpOption) {
        //如果没有提供-classpath/-cp选项，则使用当前目录作为用户类路径
        if (cpOption == null) {
            cpOption = ".";
        }
        userClasspath = Entry.create(cpOption);
    }

    /**
     * 解析jre目录
     *
     * @param jreDir
     * @return
     */
    private String parseJreDir(String jreDir) {
        //优先使用输入的-Xjre选项作为jre目录
        if (jreDir != null && Files.exists(Paths.get(jreDir))) {
            return jreDir;
        }
        //否则在当前目录下寻找jre
        if (Files.exists(Paths.get("./jre"))) {
            return "./jre";
        }
        //找不到，则使用JAVA_HOME环境变量
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != null) {
            //javahome/jre
            return Paths.get(javaHome, "jre").toString();
        }
        throw new RuntimeException("Can not find JRE folder");
    }

    /**
     * 读取class文件
     * @param classname
     * @return
     * @throws Exception
     */
    public byte[] readClass(String classname) throws Exception {
        classname = classname + ".class";
        //启动类路径
        try {
            return bootClasspath.readClass(classname);
        } catch (Exception e) {

        }
        //扩展类路径
        try {
            return extClasspath.readClass(classname);
        } catch (Exception e) {

        }
        //用户类路径
        return userClasspath.readClass(classname);
    }
}
