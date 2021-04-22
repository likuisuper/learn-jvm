package com.cxylk.parttwo;

/**
 * @Classname ThreadContextClassLoader
 * @Description 线程上下文类加载器。可以通过Thread获取，为了解决双亲委派的缺陷而生
 * @Author likui
 * @Date 2021/4/16 16:44
 **/
public class ThreadContextClassLoader {
    public static void main(String[] args) {
        //获取
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
        //设置
        Thread.currentThread().setContextClassLoader(new CustomizeClassLoader1());
        ClassLoader contextClassLoader1 = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader1);
    }
}
