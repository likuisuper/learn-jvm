package com.cxylk.partfive;

/**
 * @Classname InternDemo
 * @Description intern方法演示
 * @Author likui
 * @Date 2021/4/24 23:35
 **/
public class InternDemo {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        String s1="1";
        String s2="1";
        String s3=s1+s2;
        //放入常量池并返回引用
        s3.intern();
        String str="11";
        System.out.println(s3==str);
    }
}
