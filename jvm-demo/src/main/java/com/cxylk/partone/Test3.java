package com.cxylk.partone;

/**
 * @Classname Test3
 * @Description 被动引用3：常量在编译阶段会存入调用类的常量池中，本质上没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
 * @Author likui
 * @Date 2021/4/11 20:20
 **/
public class Test3 {
    public static void main(String[] args) {
        System.out.println(Const.HELLOWORD);
    }
}

class Const{
    public static final String HELLOWORD="hello word";
    static {
        System.out.println("A...");
    }
}
