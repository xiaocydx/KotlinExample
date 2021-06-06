package com.kotlin.example.classloader

/**
 * 不同ClassLoader加载的类，是不同的类
 *
 * @author xcc
 * @date 2021/5/30
 */
fun main() {
    val myClassLoader = MyClassLoader()
    val testClass =
            myClassLoader.loadClass("com.kotlin.example.classloader.TestClassA")
    println("$testClass | ${testClass.classLoader}")
    println("${TestClassA::class.java} | ${TestClassA::class.java.classLoader}")
    println(testClass == TestClassA::class.java)
}