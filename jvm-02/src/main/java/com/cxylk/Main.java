package com.cxylk;

import com.cxylk.classpath.Classpath;

/**
 * @Classname Main
 * @Description TODO
 * @Author likui
 * @Date 2021/4/22 21:23
 **/
public class Main {
    public static void main(String[] args) {
        Cmd cmd=Cmd.parse(args);
        if(!cmd.ok||cmd.helpFlag){
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if(cmd.versionFlag){
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd){
        Classpath cp=new Classpath(cmd.jre,cmd.classpath);
        System.out.printf("classpath：%s class：%s args：%s\n",cp,cmd.getMainClass(),cmd.getAppArgs());
        //获取className
        String className=cmd.getMainClass().replace(".","/");
        try{
            byte[] classDate = cp.readClass(className);
            System.out.println("classDate：");
            for (byte b : classDate) {
                //16进制输出
                System.out.print(String.format("%02x",b&0xff)+"");
            }
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + cmd.getMainClass());
            e.printStackTrace();
        }
    }
}
