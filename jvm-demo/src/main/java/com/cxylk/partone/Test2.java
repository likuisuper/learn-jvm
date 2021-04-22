package com.cxylk.partone;

/**
 * @Classname Test2
 * @Description 被动引用二：通过数组定义来引用类，不会触发初始化
 * @Author likui
 * @Date 2021/4/11 20:11
 **/
public class Test2 {
    public static void main(String[] args) {
        TestA[] testAS=new TestA[1];//只是将TestA当初数组类型来用，并不会初始化
//        TestA testA=new TestA();
        System.out.println("end");
    }
}

class TestA{
    static {
        System.out.println("testa");
    }
}
