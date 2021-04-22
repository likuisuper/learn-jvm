package com.cxylk.partone;

/**
 * @Classname Test5
 * @Description 反射触发初始化
 * @Author likui
 * @Date 2021/4/11 21:01
 **/
public class Test5 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> forName = Class.forName("com.cxylk.partone.Invoke");
        System.out.println("end");
    }
}

class Invoke{
    static {
        System.out.println("invoke...");
    }
}
