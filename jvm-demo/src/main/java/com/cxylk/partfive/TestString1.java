package com.cxylk.partfive;

/**
 * @Classname TestString1
 * @Description 创建字符串的方式不同，创建的对象数量也不同
 * @Author likui
 * @Date 2021/4/24 21:27
 **/
public class TestString1 {
    public static void main(String[] args) {
//        String s1="11";
        test1();
//        test2();
    }

    public static void test1(){
        String s1="11";
        String s2="11";
        System.out.println(s1==s2);
    }

    public static void test2(){
        String s1=new String("aa");
        String s2=new String("aa");
        System.out.println(s1==s2);
    }
}
