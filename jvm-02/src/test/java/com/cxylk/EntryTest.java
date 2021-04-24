package com.cxylk;

import com.cxylk.classpath.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

/**
 * @Classname EntryTest
 * @Description TODO
 * @Author likui
 * @Date 2021/4/23 11:23
 **/
public class EntryTest {
    private Entry entry;

    @Test
    public void create(){
        Assert.assertEquals(DirEntry.class,Entry.create(".").getClass());
        Assert.assertEquals(CompositeEntry.class,Entry.create("abc.jar"+File.pathSeparator+"def.jar").getClass());
        Assert.assertEquals(WildcardEntry.class,Entry.create("abc/def/*").getClass());
    }

    @Test
    public void dirEntry() throws Exception {
        String className = EntryTest.class.getName().replace('.','/')+".class";
        System.out.println(className);
        //获取路径，并且去掉包名。目的就是得到该类所在的目录
        String path  = EntryTest.class.getResource("EntryTest.class").getPath().replace(className,"");
        System.out.println(path);
        //必须要去掉开头的 / ，否则会报错
        String substring = path.substring(1);
        System.out.println(path);
        entry=Entry.create(substring);
        Assert.assertEquals(DirEntry.class,entry.getClass());
        //读取目录下的class文件
        byte[] bytes = entry.readClass(className);
        Assert.assertNotNull(bytes);
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void zipEntry() throws Exception {
        //;号分隔类路径下的所有jar包
        String[] cp = System.getProperty("java.class.path").split(File.pathSeparator);
        System.out.println(Arrays.toString(cp));
        //获取其中的junit-4.9(和pom依赖中引入的版本一致)
        String rtJarPath = Arrays.stream(cp)
                .filter(path -> path.endsWith("junit-4.9.jar"))
                .findFirst()
                .get();
        System.out.println(rtJarPath);
        entry=Entry.create(rtJarPath);
        //读取该jar包中的Test.class文件（该文件在jar包存在）
        byte[] bytes = entry.readClass("org/junit/Test.class");
        Assert.assertNotNull(bytes);
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void compositeEntryTest() throws Exception {
        String property = System.getProperty("java.class.path");
        entry=Entry.create(property);
        Assert.assertEquals(CompositeEntry.class,entry.getClass());
        byte[] bytes = entry.readClass("org/junit/Test.class");
        System.out.println(Arrays.toString(bytes));
        Assert.assertNotNull(bytes);
    }

    @Test
    public void wildcardEntryTest() throws Exception {
        String[] strings = System.getProperty("java.class.path").split(File.pathSeparator);
        String rtJarPath=Arrays.stream(strings)
                .filter(path->path.endsWith("junit-4.9.jar"))
                .findFirst()
                .get()
                .replace("junit-4.9.jar","*");
        System.out.println(rtJarPath);
        entry=Entry.create(rtJarPath);
        Assert.assertEquals(WildcardEntry.class,entry.getClass());
        byte[] data = entry.readClass("org/junit/Test.class");
        Assert.assertNotNull(data);
    }
}
