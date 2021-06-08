package com.cxylk.agent02;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname MyMonitorTransformer
 * @Description 监控方法执行耗时
 * @Author likui
 * @Date 2021/6/8 17:07
 **/
public class MyMonitorTransformer implements ClassFileTransformer {
    private static final Set<String> classNameSet=new HashSet<>();

    static {
        classNameSet.add("com.cxylk.agent02.MyAgentTest02");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try{
            String currentClassName = className.replace("/", ".");
            //如果不是set集合中的类，直接返回，不需要转换
            if(!classNameSet.contains(currentClassName)){
                return null;
            }
            System.out.println("transform:["+currentClassName+"]");
            CtClass ctClass = ClassPool.getDefault().get(currentClassName);
            //获取类中声明的所有构造函数和方法
            CtBehavior[] methods = ctClass.getDeclaredBehaviors();
            for (CtBehavior method : methods) {
                enhanceMethod(method);
            }
            return ctClass.toBytecode();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void enhanceMethod(CtBehavior method) throws CannotCompileException {
        if(method==null){
            return;
        }
        String methodName = method.getName();
        //如果是main方法则返回
        if("main".equals(methodName)){
            return;
        }
        StringBuilder source=new StringBuilder();
        //前置增强，加入时间戳，并保留现有逻辑
        source.append("{")
                .append("long start=System.nanoTime();\n") //方法之前插入语句
                .append("$_=$proceed($$);\n") //调用原来方法
                .append("System.out.print(\"method:[") //方法之后插入语句
                .append(methodName).append("]\");").append("\n")
                .append("System.out.println(\"cost:[\"+(System.nanoTime()-start)+\"ns]\");")
                .append("}");
        ExprEditor editor=new ExprEditor(){
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                //将给定的语句替换为表达式
                m.replace(source.toString());
            }
        };
        method.instrument(editor);
    }
}
