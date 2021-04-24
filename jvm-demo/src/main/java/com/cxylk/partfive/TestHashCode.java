package com.cxylk.partfive;

/**
 * @Classname TestHashCode
 * @Description 计算hashcode值
 * @Author likui
 * @Date 2021/4/24 21:04
 **/
public class TestHashCode {
    public static void main(String[] args) {
        String str1="abc";
        String str2=new String("abc");
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());
        //相同内容的hashcode是相同的，即使他们的引用不相同
        System.out.println(str1.hashCode()==str2.hashCode());
    }

}
