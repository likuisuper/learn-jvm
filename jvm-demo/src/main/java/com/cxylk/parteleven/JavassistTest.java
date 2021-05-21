package com.cxylk.parteleven;


import javassist.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;

/**
 * @Classname JavassistTest
 * @Description 字节码编译工具javassist使用
 * @Author likui
 * @Date 2021/5/21 19:29
 **/
public class JavassistTest {
    @Test
    public void test1() throws NotFoundException, CannotCompileException, IOException {
        //1.类池。底层是一个hashtable,会缓存类，可能导致内存溢出
        ClassPool pool=new ClassPool();
        //2.系统路径
        pool.appendSystemPath();
        //3.加载字节码，解析成ctClass
        CtClass ctClass = pool.get("com.cxylk.parteleven.HelloWord");
        //4.获取该类方法
        CtMethod method=ctClass.getDeclaredMethods()[0];
        //在方法之前插入(方法的开始行)，插入的语句和正常的java语句一样
        method.insertBefore("System.out.println(System.currentTimeMillis());");
        //方法后插入(方法的结束行)
        method.insertAfter("System.out.println(System.currentTimeMillis());");
        //指定行插入
        method.insertAt(11,"System.out.println(\"11行\");");
        byte[] bytes = ctClass.toBytecode();
        //动态加载到classLoader，这个类没有被加载过才可以
        ctClass.toClass();
        //此时这个类已经被加载过了，所以再插入语句会报错
//        method.insertAfter("System.out.println(System.currentTimeMillis());");
        //写入class文件，发现已经插入指定的语句
        Files.write(new File(System.getProperty("user.dir")+"/target/classes/com/cxylk/parteleven/hello.class").toPath(),bytes);
        //执行
        new HelloWord().hello("cxylk");
    }

    /**
     * 获取执行时间
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    @Test
    public void test2() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool=new ClassPool();
        pool.appendSystemPath();
        CtClass ctClass = pool.get("com.cxylk.parteleven.HelloWord");
        CtMethod method=ctClass.getDeclaredMethods()[0];
        //插入一个long类型的局部变量
        method.addLocalVariable("begin",CtClass.longType);
        //方法前插入
        method.insertBefore("begin=System.currentTimeMillis();");
        //方法后插入
        method.insertAfter("long end=System.currentTimeMillis();\n" +
                "System.out.println(end-begin);");
        ctClass.toClass();
        Files.write(new File(System.getProperty("user.dir")+"/target/classes/com/cxylk/parteleven/hello.class").toPath(),ctClass.toBytecode());
        new HelloWord().hello("cxylk");
    }

    /**
     * 添加try catch 不能添加finally
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
        ClassPool pool=new ClassPool();
        pool.appendSystemPath();
        CtClass ctClass = pool.get("com.cxylk.parteleven.HelloWord");
        CtMethod method=ctClass.getDeclaredMethods()[0];
        //给该方法添加一个long类型的参数
        method.addParameter(CtClass.longType);
        //$2表示使用第二个方法参数
        method.insertBefore("$2=System.currentTimeMillis();");
        //添加catch块
        method.addCatch("$e.printStackTrace();\n"+
                "long end=System.currentTimeMillis();\n"+
                "System.out.println(end-$2);\n"+
                "throw $e;",pool.get(Throwable.class.getName()));
        method.insertAfter("long end=System.currentTimeMillis();\n"+
                "System.out.println(end-$2);");
        ctClass.toClass();
        Files.write(new File(System.getProperty("user.dir")+"/target/classes/com/cxylk/parteleven/hello.class").toPath(),ctClass.toBytecode());
        HelloWord helloWord=new HelloWord();
        helloWord.getClass().getDeclaredMethods()[0].invoke(helloWord,"cxylk",23);
    }

    /**
     * copy method
     * @throws Exception
     */
    @Test
    public void test4() throws Exception{
        ClassPool pool=new ClassPool();
        pool.appendSystemPath();
        CtClass ctClass = pool.get("com.cxylk.parteleven.HelloWord");
        CtMethod method=ctClass.getDeclaredMethods()[0];
        //拷贝新方法
        CtMethod newMethod = CtNewMethod.copy(method, ctClass, null);
        //为该方法取个名字
        newMethod.setName(method.getName()+"$agent");
        //将该方法添加到类中
        ctClass.addMethod(newMethod);
        //添加监控代码，其中调用了copy的新方法
        //$r表示返回结果的类型，用于强制转换，$$表示所有实参
        method.setBody(String.format("{long begin = System.currentTimeMillis();\n" +
                "        try{\n" +
                "           return ($r)%s$agent($$);\n" +
                "        }finally {\n" +
                "            System.out.println(System.currentTimeMillis()-begin);\n" +
                "        }}", method.getName()));
        ctClass.toClass();
        Files.write(new File(System.getProperty("user.dir")+"/target/classes/com/cxylk/parteleven/hello.class").toPath(),ctClass.toBytecode());
        new HelloWord().hello("cxylk");
    }

    /**
     * 特殊语法-基本信息
     * @throws Exception
     */
    @Test
    public void test5() throws Exception{
        ClassPool pool=new ClassPool();
        pool.appendSystemPath();
        CtClass ctClass = pool.get("com.cxylk.parteleven.HelloWord");
        CtMethod method=ctClass.getDeclaredMethods()[0];
        method.insertAfter("System.out.println(\"this:\"+$0);\n"+
                "       System.out.println(\"参数1:\"+$1);\n"+
                "       System.out.println(\"参数数组:\"+$args);\n"+
                "       System.out.println(\"参数类型数组:\"+$sig);\n"+
                "       System.out.println(\"方法返回类型:\"+$type);\n"+
                "       System.out.println(\"当前类:\"+$class);");
        ctClass.toClass();
        new HelloWord().hello("cxylk");
    }

    @Test
    public void test9() throws Exception{
        UserServiceImpl target=new UserServiceImpl();
        InvocationHandler invoker = InvokerBuild.makeInvoker(target);
        UserService userService=(UserService)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{UserService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try{
                    System.out.println("代理 前置逻辑");
                    return invoker.invoke(proxy,method,args);
                }finally {
                    System.out.println("代理 后置逻辑");
                }
            }
        });
        userService.getName(11);
    }
}
