package com.cxylk.partsix;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Classname HeapOverFlowTest1
 * @Description GC调优 1、-Xms10m -Xmx10m；2、-Xms100m -Xms100m
 * @Author likui
 * @Date 2021/4/29 23:23
 **/
public class HeapOverFlowTest1 {
    private int[] arr=new int[1024*1024];//分配1M内存

    public static void main(String[] args) throws InterruptedException {
        while (true){
            TimeUnit.MILLISECONDS.sleep(50);
            run();
        }
    }

    public static void run(){
        List<HeapOverFlowTest1> list=new ArrayList<>();
        list.add(new HeapOverFlowTest1());
    }
}
