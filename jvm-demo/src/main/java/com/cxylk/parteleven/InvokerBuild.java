package com.cxylk.parteleven;

import javassist.*;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname InvokerBuild
 * @Description java动态有一个环节必须使用反射，在一定程度上会影响性能，可以通过构建一个
 *              invoker直接调用代理目标，从而避免反射的调用
 * @Author likui
 * @Date 2021/5/21 22:36
 **/
public class InvokerBuild {
    public static InvocationHandler makeInvoker(Object target) throws Exception {
        String newClassName = target.getClass().getName() + "$Invoker";
        try {
            Class<?> aClass1 = target.getClass().getClassLoader().loadClass(newClassName);
            return (InvocationHandler) aClass1.getConstructor(target.getClass()).newInstance(target);
        } catch (ClassNotFoundException e) {
            // 勿略异常
        }
        ClassPool pool = ClassPool.getDefault();
        pool.importPackage(target.getClass().getPackage().getName());
        pool.importPackage("java.lang.reflect");
        //构建一个空类
        CtClass ctClass = pool.makeClass(newClassName);
        //实现的接口
        ctClass.addInterface(pool.get("java.lang.reflect.InvocationHandler"));
        // 添加属性
        ctClass.addField(CtField.make(String.format("private %s target;", target.getClass().getSimpleName()),ctClass));
        //添加构造 方法
        CtConstructor ctConstructor = CtNewConstructor.make(String.format("public %s(%s target) {\n" +
                "            this.target = target;\n" +
                "        }", ctClass.getSimpleName(), target.getClass().getSimpleName()), ctClass);
        ctClass.addConstructor(ctConstructor);

        CtMethod invokerMethod = CtNewMethod.make(" public Object invoke(Object proxy,Method method, Object[] args) throws Throwable {return null;}", ctClass);
        StringBuilder builder=new StringBuilder();
        for (Method method : target.getClass().getDeclaredMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            String name = method.getName();
            Stream.Builder<String> parameterBuilder = Stream.builder();
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterBuilder.add(String.format("(%s)$3[%s]",parameterTypes[i].getName(), i));
            }
            String patamStr = parameterBuilder.build().collect(Collectors.joining(","));
            if (method.getReturnType().equals(void.class)) {
                builder.append(String.format("if (name.equals(\"%s\")) {\n" +
                        "target.%s(%s);\n"                                          +
                        "                return null;\n" +
                        "            }",name,name,patamStr ));
                builder.append("\n");
            }else{
                builder.append(String.format("if (name.equals(\"%s\")) {\n" +
                        "\n"                                          +
                        "                return ($r)target.%s(%s);\n" +
                        "            }",name,name,patamStr ));
                builder.append("\n");
            }

        }

        String body = String.format("{ String name = $2.getName();\n" +
                "%s" +
                "        if(name.equals(\"toString\")){\n" +
                "            return target.toString();\n" +
                "        }\n" +
                "        if(name.equals(\"hashCode\")){\n" +
                "            return ($w)target.hashCode();\n" +
                "        }\n" +
                "        return $2.invoke(target,$3);}", builder.toString());
        invokerMethod.setBody(body);

        ctClass.addMethod(invokerMethod);

        Files.write(new File(System.getProperty("user.dir")+"/target/classes/com/cxylk/parteleven/hello.class").toPath(),ctClass.toBytecode());
        Class aClass = ctClass.toClass();
        return (InvocationHandler) aClass.getConstructor(target.getClass()).newInstance(target);
    }
}
