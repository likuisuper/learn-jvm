package com.cxylk.asm01;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Classname HelloWordByAsm
 * @Description 使用asm字节码工具输出hello word
 * @Author likui
 * @Date 2021/6/9 14:01
 **/
public class HelloWordByAsm extends ClassLoader{

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //生成二进制字节码
        byte[] bytes = generate();
        //输出字节码
        outputClazz(bytes);
        //加载AsmHelloWord
        Class<?> clazz=new HelloWordByAsm().defineClass("com.cxylk.asm01.AsmHelloWord",bytes,0,bytes.length);
        //反射获取main方法
        Method main = clazz.getMethod("main", String[].class);
        //调用main方法
        //数组类型的参数必须包含在new Object[]{}中，否则会报IllegalArgumentException
        main.invoke(null,new Object[]{new String[]{}});
    }

    public static byte[] generate(){
        //生成一个包含编译后的类的字节数组（构建类的字节数组）
        //参数0代表asm不会自动计算帧、局部变量和操作数栈的大小
        ClassWriter cw=new ClassWriter(0);
        //定义对象头，包括版本号、访问修饰符、全类名、签名（对应泛型）、父类、实现的接口
        cw.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"com/cxylk/asm01/AsmHelloWord",null,"java/lang/Object",null);
        //添加方法，包括访问修饰符、方法名、描述符、签名（对应泛型）、异常
        MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        //执行指令，获取静态属性
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        //加载常量 load constant
        methodVisitor.visitLdcInsn("hello word");
        //调用方法
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
        //void 函数返回
        methodVisitor.visitInsn(Opcodes.RETURN);
        //设置操作数栈的深度和局部变量表大小
        methodVisitor.visitMaxs(2,1);
        //方法结束
        methodVisitor.visitEnd();
        //类完成
        cw.visitEnd();
        //生成字节数组
        return cw.toByteArray();
    }

    public static void outputClazz(byte[] bytes) throws IOException {
        String path=HelloWordByAsm.class.getResource("/").getPath()+"AsmHelloWord.class";
        try(FileOutputStream out=new FileOutputStream(new File(path))){
            System.out.println("ASM类输出路径："+path);
            out.write(bytes);
        }
    }
}
