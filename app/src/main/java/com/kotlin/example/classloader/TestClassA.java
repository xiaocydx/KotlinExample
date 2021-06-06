package com.kotlin.example.classloader;

/**
 * @author xcc
 * @date 2021/5/30
 */
public class TestClassA {

    public static void run() {
        new TestClassB().protectedMethod();
    }
}
