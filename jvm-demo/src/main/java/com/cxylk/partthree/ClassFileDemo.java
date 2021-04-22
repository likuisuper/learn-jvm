package com.cxylk.partthree;

/**
 * @Classname ClassFileDemo
 * @Description
 * @Author likui
 * @Date 2021/4/17 20:16
 **/
public class ClassFileDemo {
    //字段描述符
//    byte v1;//B
//    short v2;//S
//    int v3;//I
//    long v4;//注意是J
//    char v5;//C
//    float v6;//F
//    double v7;//D
//    boolean v8;//Z
    int[] v9;//数组类型 [I 二维数组就是[[I 后面类推
    String[] v10;//引用类型 [LJava/lang/String; 最后面有一个分号用来表示结束 只有引用类型有分号

    //(LJava/lang/String;)V
    public static void main(String[] args) {
        int c;
        System.out.println("hello word");
    }

    //(LJava/lang/String;[II)J
//    long test(String a,int[] b,int c){return 0;}
}
