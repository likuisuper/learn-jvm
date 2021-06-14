package com.cxylk.asm01;


import com.cxylk.util.OutPutClassUtil;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM5;

/**
 * @Classname TestMonitor
 * @Description 使用asm对监视方法
 * @Author likui
 * @Date 2021/6/10 16:56
 **/
public class TestMonitor extends ClassLoader{
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //解析编译过的class字节数组
        ClassReader cr=new ClassReader(MyMethod.class.getName());
        //构造一个新的cw对象
        ClassWriter cw=new ClassWriter(cr,ClassWriter.COMPUTE_MAXS);
        {
            MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitVarInsn(Opcodes.ALOAD,0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(1,1);
            methodVisitor.visitEnd();
        }
        ClassVisitor cv=new ProfilingClassAdapter(cw,MyMethod.class.getSimpleName());
        //将新增的指令加入到原来的字节数组中。EXPAND_FRAMES表示栈图始终以扩展格式访问
        cr.accept(cv,ClassReader.EXPAND_FRAMES);
        byte[] bytes = cw.toByteArray();
        OutPutClassUtil.outputClazz(bytes,"AsmTestMonitor");
        Class<?> clazz = new TestMonitor().defineClass("com.cxylk.asm01.MyMethod", bytes, 0, bytes.length);
        Method method = clazz.getMethod("queryUserInfo", String.class);
        Object result = method.invoke(clazz.getConstructor().newInstance(), "1000");
        System.out.println(result);

    }

    static class ProfilingClassAdapter extends ClassVisitor {

        public ProfilingClassAdapter(final ClassVisitor cv,String innerClassName) {
            super(ASM5,cv);
        }

        public MethodVisitor visitMethod(int access,String name,String desc,String signature,String[] exceptions){
            System.out.println("access："+access);
            System.out.println("name："+name);
            System.out.println("desc："+desc);
            if(!"queryUserInfo".equals(name))
                return null;
            MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
            return new ProfilingMethodVisitor(methodVisitor,access,name,desc);
        }
    }

    static class ProfilingMethodVisitor extends AdviceAdapter{
        private String methodName="";

        protected ProfilingMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(ASM5,methodVisitor, access, name, descriptor);
            this.methodName=name;
        }

        @Override
        protected void onMethodEnter() {
            //AdviceAdapter继承了MethodVisitor，mv是MethodVisitor类中的变量
            mv.visitMethodInsn(INVOKESTATIC,"java/lang/System","nanoTime","()J",false);
            //将nanoTime方法返回的变量存入第3个本地变量 -->long timer=System.nanoTime();
            mv.visitVarInsn(LSTORE,2);
            //将参数uid推送至栈顶（打印方法入参）
            mv.visitVarInsn(ALOAD,1);
        }

        @Override
        protected void onMethodExit(int opcode) {
            //添加的指令要在xReturn或者ATHROW之前，因为这些指令都会结束方法的执行
            if((opcode>=IRETURN&&opcode<=RETURN)||opcode==ATHROW){
                //获取System.out静态字段，返回PrintStream类型，可以调用println方法
                mv.visitFieldInsn(GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
                //创建StringBuilder对象
                mv.visitTypeInsn(NEW,"java/lang/StringBuilder");
                //复制栈顶数值并压入栈顶
                //StringBuilder=new StringBuilder()后，要给局部变量表的this赋值
                mv.visitInsn(DUP);
                //调用构造函数完成初始化
                mv.visitMethodInsn(INVOKESPECIAL,"java/lang/StringBuilder","<init>","()V",false);
                //将字符串常量推至栈顶
                mv.visitLdcInsn("方法执行耗时(纳秒)->"+methodName+": ");
                //调用sb的append方法
                mv.visitMethodInsn(INVOKEVIRTUAL,"java/lang/StringBuilder","append","(Ljava/lang/String;)Ljava/lang/StringBuilder;",false);
                mv.visitMethodInsn(INVOKESTATIC,"java/lang/System","nanoTime","()J",false);
                //将enter方法中局部变量表保存的timer取出至栈顶
                mv.visitVarInsn(LLOAD,2);
                //相减得到方法耗时
                mv.visitInsn(LSUB);

                mv.visitMethodInsn(INVOKEVIRTUAL,"java/lang/StringBuilder","append","(J)Ljava/lang/StringBuilder;",false);
                mv.visitMethodInsn(INVOKEVIRTUAL,"java/lang/StringBuilder","toString","()Ljava/lang/String;",false);
                mv.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
            }
        }
    }
}
