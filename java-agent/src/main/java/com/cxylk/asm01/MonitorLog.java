package com.cxylk.asm01;

import com.cxylk.util.OutPutClassUtil;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM5;

/**
 * @Classname MonitorLog
 * @Description TODO
 * @Author likui
 * @Date 2021/6/13 15:51
 **/
public class MonitorLog extends ClassLoader{
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //解析编译过的class字节数组
        ClassReader cr=new ClassReader(MyMethod.class.getName());
        //构造一个新的cw对象
        ClassWriter cw=new ClassWriter(cr,ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv=new ProfilingClassAdapter(cw,MyMethod.class.getSimpleName());
        //将新增的指令加入到原来的字节数组中。EXPAND_FRAMES表示栈图始终以扩展格式访问
        cr.accept(cv,ClassReader.EXPAND_FRAMES);
        byte[] bytes = cw.toByteArray();
        OutPutClassUtil.outputClazz(bytes,"AsmTestMonitorLog");
        Class<?> clazz = new MonitorLog().defineClass("com.cxylk.asm01.MyMethod", bytes, 0, bytes.length);
        Method method = clazz.getMethod("sum", int.class,int.class);
        Object result = method.invoke(clazz.getConstructor().newInstance(), 6,3);
        System.out.println(result);

    }

    static class ProfilingClassAdapter extends ClassVisitor {

        public ProfilingClassAdapter(final ClassVisitor cv,String innerClassName) {
            super(ASM5,cv);
        }


        public MethodVisitor visitMethod(int access,String name,String desc,String signature,String[] exceptions){
            if(!"sum".equals(name))
                return super.visitMethod(access, name, desc, signature, exceptions);
            System.out.println("access："+access);
            System.out.println("name："+name);
            System.out.println("desc："+desc);
            System.out.println("signature："+signature);
            MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
            return new ProfilingMethodVisitor(methodVisitor,access,name,desc);
        }
    }

    static class ProfilingMethodVisitor extends AdviceAdapter {
        private String methodName = "";

        protected ProfilingMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(ASM5, methodVisitor, access, name, descriptor);
            this.methodName = name;
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            super.visitVarInsn(opcode, var);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }

        @Override
        protected void onMethodEnter() {
            // 输出方法
            mv.visitLdcInsn(methodName);
            //将2推送至栈顶，表示下面创建的数组容量，因为栈是先进后出的
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            //数组0号索引
            mv.visitInsn(ICONST_0);
            //将参数1从局部变量表推送至栈顶
            mv.visitVarInsn(ILOAD, 1);
            //将栈顶的int型数值也就是参数1存入数组0号索引
            mv.visitInsn(IASTORE);
            //数组是连续的，所以需要复制地址才能找到下一个
            mv.visitInsn(DUP);
            //数组1号索引
            mv.visitInsn(ICONST_1);
            //将参数2从局部变量表推送至栈顶
            mv.visitVarInsn(ILOAD, 2);
            //给数组索引1赋值为参数2
            mv.visitInsn(IASTORE);
            mv.visitMethodInsn(INVOKESTATIC,"com/cxylk/asm01/TestMonitorLog","info","(Ljava/lang/String;[I)V",false);
        }
    }
}
