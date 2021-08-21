/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.cxtlk;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Fork(1)
@BenchmarkMode(Mode.AverageTime) //平均时间
@Warmup(iterations=3)//预热,控制预热次数
@Measurement(iterations=3)//正式执行成为测试结果的依据，控制测试次数
@OutputTimeUnit(TimeUnit.NANOSECONDS) //程序执行是很快的，所以使用纳秒
public class MyBenchmark {
    static int a=0;

    static String s1 = "a";
    static String s2 = "b";
    static String s3 = "c";

    /**
     * 无锁的方法
     */
    @Benchmark
    public void testMethod() {
        // place your benchmarked code here
        a++;
    }

    /**
     * 显示锁的方法。虽然加了锁，但是会发生锁消除
     */
    @Benchmark
    public void testMethodLock(){
        Object object=new Object();
        synchronized (object){
            a++;
        }
    }

    /**
     * 没有手动加锁，但是底层还是会加锁
     */
    @Benchmark
    public void concatString(){
        StringBuffer sb=new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        sb.append(s3);
        sb.toString();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
