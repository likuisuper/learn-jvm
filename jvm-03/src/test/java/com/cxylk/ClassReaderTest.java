package com.cxylk;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Classname ClassReaderTest
 * @Description 读取字节码测试
 * @Author likui
 * @Date 2021/4/25 14:21
 **/
public class ClassReaderTest {
    //字节流数组
    private byte[] classDate={
            -54, -2, -70, -66, 0, 0, 0, 52, 2, 26, 3, 0, 0, -40, 0, 3, 0, 0, -37, -1, 3, 0, 0, -33, -1, 3, 0, 1, 0, 0, 8, 0,
            59, 8, 0, 83, 8, 0, 86, 8, 0, 87, 8, 0, 110, 8, 0, -83, 8, 0, -77, 8, 0, -49, 8, 0, -47, 1, 0, 3, 40, 41, 73, 1,
            0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 1, 0, 20, 40, 41,
            76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 3, 40, 41, 86, 1, 0, 3,
            40, 41, 90, 1, 0, 4, 40, 41, 91, 66, 1, 0, 4, 40, 41, 91, 67, 1, 0, 4, 40, 67, 41, 67, 1, 0, 21, 40, 68, 41, 76,
            106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 4, 40, 73, 41, 67, 1, 0, 4};

    @Test
    public void test1(){
        byte b=classDate[0];
        //byte的取值范围是-128~127，为了读取到的数不超过这个范围，所以需要取模，
        //而位元素比取模块，所以做法就是 &0x0ff ，这样就能让读取到的数始终在byte的取值范围内
        System.out.println("做与运算："+(b&0x0ff));
    }

    /**
     * 魔数校验
     */
    @Test
    public void readAndCheckMagic(){
        byte[] bytes=new byte[4];
        //去classDate的前四位
        System.arraycopy(classDate,0,bytes,0,4);
        //将4位byte字节转换为16进制字符串
        String magic_hex_str=new BigInteger(1,bytes).toString(16);
        System.out.println("magic_hex_str："+magic_hex_str);
        //magic_hex_str是16进制的字符串cafebabe，因为java中没有无符号整型，所以如果想要无符号，只能放到更高位中
        //如果这里使用Integer来解析的话是会报错的，因为解析结果超过了Int所能表示的正整数最大值
        long magic_unsigned_int32 = Long.parseLong(magic_hex_str, 16);
        System.out.println("magic_unsigned_int32："+magic_unsigned_int32);
    }
}
