package com.cxylk;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * @Classname Hello
 * @Description jcommander解析命令行参数。官网：https://jcommander.org/
 * @Author likui
 * @Date 2021/4/22 17:43
 **/
public class Hello {
    @Parameter(names = {"--length","-l"})
    int length;

    @Parameter(names = {"--pattern","-p"})
    int pattern;

    public static void main(String[] args) {
        Hello hello=new Hello();
        JCommander.newBuilder()
                .addObject(hello)
                .build()
                .parse(args);
        hello.run();
    }

    public void run(){
        System.out.printf("%d %d",length,pattern);
    }
}
