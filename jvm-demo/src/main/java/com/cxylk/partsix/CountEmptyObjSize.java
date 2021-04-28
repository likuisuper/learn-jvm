package com.cxylk.partsix;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Classname CountEmptyObjSize
 * @Description 计算空对象的大小
 * @Author likui
 * @Date 2021/4/26 21:29
 **/
public class CountEmptyObjSize {
    public static void main(String[] args) {
        CountEmptyObjSize obj=new CountEmptyObjSize();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
//        while (true);
    }
}
