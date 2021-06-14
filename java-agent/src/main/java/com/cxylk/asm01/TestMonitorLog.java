package com.cxylk.asm01;

/**
 * @Classname TestMonitorLog
 * @Description TODO
 * @Author likui
 * @Date 2021/6/13 16:08
 **/
public class TestMonitorLog {
    public static void info(String name,int... parameters){
        System.out.println("方法："+name);
        System.out.println("参数："+"["+parameters[0]+","+parameters[1]+"]");
    }
}
