package com.cxylk.parteleven;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Author likui
 * @Date 2021/5/21 22:25
 **/
public class UserServiceImpl implements UserService{
    @Override
    public String getName(Integer id) {
        System.out.println("执行getName方法");
        return "cxylk";
    }
}
