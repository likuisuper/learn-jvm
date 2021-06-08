package com.cxylk.agent02;

import java.lang.instrument.Instrumentation;

/**
 * @Classname MyAgent
 * @Description 通过字节码增强监控方法执行耗时
 * @Author likui
 * @Date 2021/6/8 16:24
 **/
public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("this is my agent "+agentArgs);
        MyMonitorTransformer monitor = new MyMonitorTransformer();
        inst.addTransformer(monitor);
    }
}
