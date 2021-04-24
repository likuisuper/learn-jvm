package com.cxylk.partfive;

/**
 * @Classname OopModel
 * @Description 对象模型。java中的对象在c++中是什么形式？通过hsdb查看
 * @Author likui
 * @Date 2021/4/24 17:26
 **/
public class OopModel {
    public static void main(String[] args) {
        //对象
        OopModel model=new OopModel();

        //数组
        int[] ints=new int[19];

        //对象数组。为什么要指定数组长度？如果数组长度不确定，将无法通过元数据中的信息推断出数组的大小
        OopModel[] models=new OopModel[5];

        while (true);
    }
}
