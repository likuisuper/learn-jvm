package com.cxylk;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Classname CmdTest
 * @Description TODO
 * @Author likui
 * @Date 2021/4/23 9:15
 **/
public class CmdTest {
    @Test
    public void test(){
        Assert.assertTrue(Cmd.parse(new String[]{"-?"}).helpFlag);
        Assert.assertTrue(Cmd.parse(new String[]{"-help"}).helpFlag);
        Assert.assertTrue(Cmd.parse(new String[]{"-version"}).versionFlag);
        Assert.assertFalse(Cmd.parse(new String[]{"-cp"}).ok);
        Assert.assertFalse(Cmd.parse(new String[]{"-classpath"}).ok);
        Assert.assertEquals("foo.jar", Cmd.parse(new String[]{"-cp", "foo.jar"}).classpath);
        Assert.assertEquals("foo.jar", Cmd.parse(new String[]{"-classpath", "foo.jar"}).classpath);
    }
}
