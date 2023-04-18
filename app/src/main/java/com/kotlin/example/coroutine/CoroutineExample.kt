@file:Suppress("PrivatePropertyName")

package com.kotlin.example.coroutine

/**
 * 协程测试例子
 *
 * @author xcc
 * @date 2021/5/15
 */
abstract class CoroutineExample {
    private val TAG = this::class.java.simpleName

    abstract suspend fun run()

    protected fun log(msg: Any?) {
        println("$TAG：$msg")
    }
}