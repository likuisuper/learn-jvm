package com.cxylk.partsix;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Classname CountArrayObjSize
 * @Description 计算数组对象的大小
 * @Author likui
 * @Date 2021/4/27 22:26
 **/
public class CountArrayObjSize {

    static int[] arr={1,2,3};

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
        while (true);
    }
}
