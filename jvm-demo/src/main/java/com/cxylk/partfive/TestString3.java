package com.cxylk.partfive;

/**
 * @Classname TestString3
 * @Description 双引号+new String拼接字符串
 * @Author likui
 * @Date 2021/4/24 23:26
 **/
public class TestString3 {
    public static void main(String[] args) {
        String s="a"+new String("b");
//        test1();
    }

    public static void test1() {
        String s1 = "11";
        String s2 = new String("11");
        String s3 = s1 + s2;
//        String s="11"+new String("11");
        String s4 = "1111";
//        System.out.println(s3 == s4);
    }
}
