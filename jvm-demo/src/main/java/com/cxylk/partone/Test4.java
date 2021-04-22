package com.cxylk.partone;

import java.util.UUID;

/**
 * @Classname Test4
 * @Description 和Test3进行对比
 * @Author likui
 * @Date 2021/4/11 20:45
 **/
public class Test4 {
    public static void main(String[] args) {
        System.out.println(NotConst.RANDOM);
    }
}

class NotConst{
    public static final String RANDOM= UUID.randomUUID().toString();
    static {
        System.out.println("A...");
    }
}

