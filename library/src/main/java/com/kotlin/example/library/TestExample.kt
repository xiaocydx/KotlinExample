@file:Suppress("PrivatePropertyName")

package com.kotlin.example.library

/**
 * 测试例子
 *
 * @author xcc
 * @date 2021/5/15
 */
abstract class TestExample {
    private val TAG = this::class.java.simpleName

    abstract fun run()

    protected fun log(msg: String) {
        println("$TAG：$msg")
    }
}