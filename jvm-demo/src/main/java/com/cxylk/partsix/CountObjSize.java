package com.cxylk.partsix;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Classname CountObjSize
 * @Description 计算有实例数据的对象大小
 * @Author likui
 * @Date 2021/4/26 23:49
 **/
public class CountObjSize {
    private int a=10;
    private int b=20;

    public static void main(String[] args) {
        CountObjSize countObjSize=new CountObjSize();
        System.out.println(ClassLayout.parseInstance(countObjSize).toPrintable());
    }
}
