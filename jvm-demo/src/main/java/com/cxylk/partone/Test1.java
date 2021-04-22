package com.cxylk.partone;

/**
 * @Classname Test1
 * @Description 被动引用一:通过子类引用父类的静态字段，不会导致子类的初始化
 * @Author likui
 * @Date 2021/4/11 20:05
 **/
public class Test1 {
    public static void main(String[] args) {
        System.out.println(Sub.value);
        while (true);
    }
}

class Super{
    public static String value="123";

    static {
        System.out.println("Super init...");
    }
}

class Sub extends Super{
    static {
        System.out.println("Sub init...");
    }
}
