package com.cxylk.partone;

/**
 * @Classname InitDeadLock
 * @Description 初始化时发生死锁
 * @Author likui
 * @Date 2021/4/11 19:54
 **/
public class InitDeadLock {
    public static void main(String[] args) {
        new Thread(A::test).start();
        new Thread(B::test).start();
    }
}

class A{
    static {
        System.out.println("ClassA init...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new B();
    }
    public static void test(){
        System.out.println("aaa");
    }
}

class B{
    static {
        System.out.println("ClassB init...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new A();
    }

    public static void test(){
        System.out.println();
    }
}

