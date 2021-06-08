package com.cxylk.agent01;

import java.lang.instrument.Instrumentation;

/**
 * @Classname MyAgent
 * @Description java程序启动时首先调用premain方法
 * @Author likui
 * @Date 2021/6/8 15:28
 **/
public class MyAgent {
    //JVM首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("hello JavaAgent "+agentArgs);
    }

    //如果代理类没有实现上面方法，那么JVM将尝试调用该方法
    public static void premain(String agentArgs){

    }
}
