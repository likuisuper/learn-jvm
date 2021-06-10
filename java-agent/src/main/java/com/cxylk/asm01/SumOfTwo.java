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
 * @Classname SumOfTwo
 * @Description 使用字节码完成两数之和
 * @Author likui
 * @Date 2021/6/9 16:52
 **/
public class SumOfTwo extends ClassLoader{

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        byte[] bytes = generate();
        outputClazz(bytes);
        //加载类文件
        Class<?> clazz=new SumOfTwo().defineClass("com.cxylk.asm01.AsmSumOfTwoNumbers",bytes,0,bytes.length);
        //获取sum方法
        Method sum = clazz.getMethod("sum", int.class, int.class);
        //调用
        System.out.println(sum.invoke(clazz.newInstance(),3,5));
    }

    public static byte[] generate(){
        ClassWriter cw=new ClassWriter(0);
        //第一个是构造方法
        {
            //添加方法
            MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            //将this推送至栈顶。对于非静态函数，第一个变量是this，对应操作是aload_0
            methodVisitor.visitVarInsn(Opcodes.ALOAD,0);
            //调用init方法
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            //返回操作数栈顶值
            methodVisitor.visitInsn(Opcodes.RETURN);
            //设置操作数栈和局部变量表大小
            methodVisitor.visitMaxs(1,1);
            //方法结束
            methodVisitor.visitEnd();

        }
        //第二个是sum方法
        //public int sum(int a,int b){return a+b}
        {
            //首先需要定义对象头
            cw.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"com/cxylk/asm01/AsmSumOfTwoNumbers",null,"java/lang/Object",null);
            //添加sum方法
            MethodVisitor sum = cw.visitMethod(Opcodes.ACC_PUBLIC, "sum", "(II)I", null, null);
            //将参数1和参数2推送至栈顶
            sum.visitVarInsn(Opcodes.ILOAD,1);
            sum.visitVarInsn(Opcodes.ILOAD,2);
            //将两个数相加
            sum.visitInsn(Opcodes.IADD);
            //返回相加结果
            sum.visitInsn(Opcodes.IRETURN);
            //设置操作数栈和局部变量表大小
            sum.visitMaxs(2,3);
            sum.visitEnd();
        }
        //类完成
        cw.visitEnd();
        //生成字节数组
        return cw.toByteArray();
    }

    private static void outputClazz(byte[] bytes) throws IOException {
        String path=HelloWordByAsm.class.getResource("/").getPath()+"AsmSumOfTwoNumbers.class";
        try(FileOutputStream out=new FileOutputStream(new File(path))){
            System.out.println("ASM类输出路径："+path);
            out.write(bytes);
        }
    }
}
