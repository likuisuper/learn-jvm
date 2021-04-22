package com.cxylk.parttwo;

import java.io.*;

/**
 * @Classname CustomizeClassLoader1
 * @Description 自定义类加载器，需要继承ClassLoader，如果不需要打破双亲委派，重写findClass方法，否则重写loadClass
 * @Author likui
 * @Date 2021/4/16 14:47
 **/
public class CustomizeClassLoader1 extends ClassLoader{

    public static void main(String[] args) {
        CustomizeClassLoader1 classLoader=new CustomizeClassLoader1();
        try {
            Class<?> clazz = classLoader.loadClass(Classloader_1_A.class.getName());
            System.out.println(clazz);
            //没有走自定义的类加载器，因为双亲委派模型，自定义类加载器的父类能够加载这个类
            System.out.println(clazz.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final String SUFFIX = ".class";

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        System.out.println("CustomizeClassLoader1 findClass");

        byte[] data = getData(className.replace('.', '/'));

        return defineClass(className, data, 0, data.length);
    }

    private byte[] getData(String name) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;

        File file = new File(name + SUFFIX);
        if (!file.exists()) return null;

        try {
            inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();

            int size = 0;
            byte[] buffer = new byte[1024];

            while ((size = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, size);
            }

            return outputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }
}

class Classloader_1_A {

}
