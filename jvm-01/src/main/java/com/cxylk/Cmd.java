package com.cxylk;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.util.List;

/**
 * @Classname Cmd
 * @Description 使用jcommander命令行工具解析参数
 * @Author likui
 * @Date 2021/4/22 21:11
 **/
public class Cmd {

    @Parameter(names = {"-?", "-help"}, description = "print help message", order = 3, help = true)
    boolean helpFlag = false;

    @Parameter(names = "-version", description = "print version and exit", order = 2)
    boolean versionFlag = false;

    @Parameter(names = { "-cp", "-classpath" }, description = "classpath", order = 1)
    String classpath;

    @Parameter(description = "main class and args")
    List<String> mainClassAndArgs;

    boolean ok;

    String getMainClass() {
        return mainClassAndArgs != null && !mainClassAndArgs.isEmpty()
                ? mainClassAndArgs.get(0)
                : null;
    }

    List<String> getAppArgs() {
        return mainClassAndArgs != null && mainClassAndArgs.size() > 1
                ? mainClassAndArgs.subList(1, mainClassAndArgs.size())
                : null;
    }

    static Cmd parse(String[] argv) {
        Cmd args = new Cmd();
        JCommander cmd = JCommander.newBuilder()
                .addObject(args)
                .build();
        //需要添加try catch，否则解析cp和classpath会报错
        try{
            cmd.parse(argv);
            args.ok = true;
        }catch (ParameterException ignored){

        }
        return args;
    }
}
