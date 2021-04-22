package com.cxylk.partone;

/**
 * @Classname Test6
 * @Description TODO
 * @Author likui
 * @Date 2021/4/11 21:10
 **/
public class Test6 {
    public static void main(String[] args) {
        Test6_A instance = Test6_A.getInstance();
        System.out.println(instance.a);
        System.out.println(instance.b);
    }
}

class Test6_A{
    public static int a;
    public static Test6_A test6_a=new Test6_A();
    public static int b=0;
    Test6_A(){
        a++;
        b++;
    }

    public static Test6_A getInstance(){
        return test6_a;
    }
}
