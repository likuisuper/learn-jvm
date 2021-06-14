package com.cxylk.asm01;

/**
 * @Classname MyMethod
 * @Description 需要监控的方法
 * @Author likui
 * @Date 2021/6/10 16:57
 **/
public class MyMethod {
    public String queryUserInfo(String uid){
        System.nanoTime();
        System.out.println("aaa");
        System.out.println("bbb");
        System.out.println("ccc");
        return uid;
    }

    public int sum(int a,int b){
        return a+b;
    }
}
