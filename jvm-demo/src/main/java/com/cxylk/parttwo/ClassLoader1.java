package com.cxylk.parttwo;

import sun.misc.Launcher;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Classname ClassLoader1
 * @Description 查看当前类的双亲以及类加载路径,三种类加载器
 * @Author likui
 * @Date 2021/4/15 22:36
 **/
public class ClassLoader1 {
    public static void main(String[] args) {
        //使用应用类加载器加载main方法所在的类
        ClassLoader classLoader = ClassLoader1.class.getClassLoader();
        System.out.println(classLoader);

        //两者结果都是一样的，输出的都是应用类加载器。内部实现都是一样的
        System.out.println(ClassLoader.getSystemClassLoader());

        //双亲委派，应用类加载器的双亲是扩展类加载器
        System.out.println(ClassLoader.getSystemClassLoader().getParent());

        //启动类加载器，c++实现，所以为null
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());

        //查看启动类加载器的加载路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        System.out.println("<==================>");

        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        URLClassLoader urlClassLoader=(URLClassLoader)parent;
        URL[] urLs1 = urlClassLoader.getURLs();
        for (URL url : urLs1) {
            System.out.println(url);
        }

        System.out.println("<===================>");
        //查看类路径下的所有类
        String[] property = System.getProperty("java.class.path").split(":");
        for (String s : property) {
            System.out.println(s);
        }

        System.out.println("<====================>");
        URLClassLoader urlClassLoader1 = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL[] urLs2 = urlClassLoader1.getURLs();
        for (URL url : urLs2) {
            System.out.println(url);
        }
    }
}
