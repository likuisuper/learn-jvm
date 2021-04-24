package com.cxylk.partfive;

/**
 * @Classname TestString2
 * @Description 拼接字符串底层实现
 * @Author likui
 * @Date 2021/4/24 22:23
 **/
public class TestString2 {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    public static void test1(){
        //编译优化
        String s1="11"+"11";
        String s2="1111";
        System.out.println(s1==s2);
    }

    public static void test2(){
        //底层是StringBuilder的toString方法
        String s1="11";
        String s2="11";
        String s3=s1+s2;
    }

    public static void test3(){
        //不会去访问常量池，可以调用intern方法改变
        String s1=new String(new char[]{'1','1'},0,2);
//        s1.intern();
        String s2="11";
        System.out.println(s1==s2);
        System.out.println(s1.equals(s2));
    }

    public static void test4(){
        final String s1="1";
        final String s2="1";
        String s3=s1+s2;
        String s4="11";
//        System.out.println(s3==s4);
    }
}
