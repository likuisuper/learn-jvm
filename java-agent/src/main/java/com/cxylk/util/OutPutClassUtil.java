package com.cxylk.util;

import com.cxylk.asm01.HelloWordByAsm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname OutPutClassUtil
 * @Description 输入字节码
 * @Author likui
 * @Date 2021/6/10 20:55
 **/
public class OutPutClassUtil {
    public static void outputClazz(byte[] bytes,String className) throws IOException {
        String path= HelloWordByAsm.class.getResource("/").getPath()+className+".class";
        try(FileOutputStream out=new FileOutputStream(new File(path))){
            System.out.println("ASM类输出路径："+path);
            out.write(bytes);
        }
    }
}
