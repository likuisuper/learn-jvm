package com.cxylk.partfour;

/**
 * @Classname TestClass
 * @Description 使用new关键字创建一个对象，底层做了什么？
 * @Author likui
 * @Date 2021/4/20 14:57
 **/
public class TestClass {
    public void test1(){
        TestClass testClass=new TestClass();
    }

    public int test2(){
        int a=100;
        int b=200;
        int c=300;
        return (a+b)*c;
    }

}
