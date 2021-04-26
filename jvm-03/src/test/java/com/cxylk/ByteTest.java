package com.cxylk;

import org.junit.Test;

import java.math.BigInteger;

/**
 * @Classname ByteTest
 * @Description java中是没有无符号类型的，但是虚拟机解析class字节码时，是按u1,u2,u4等无符号数来读取的。
 *              所以在处理字节码时需要进行转换
 * @Author likui
 * @Date 2021/4/25 13:19
 **/
public class ByteTest {
    @Test
    public void test1(){
        byte[] val={-120};
        //第一个参数为1代表正数，-1代表负数，0表示0。所以这里需传入为1，才能输出负数的结果
        BigInteger bigInteger=new BigInteger(1,val);
        //有符号
        System.out.println(bigInteger.byteValue());//-120
        //无符号，使用增位实现，比如136=256(一个字节)-120
        //先转成16进制的字符串
        String str = bigInteger.toString(16);
        System.out.println(str);
        //使用基数16解析
        System.out.println(Integer.parseInt(str, 16));//136
    }
}
