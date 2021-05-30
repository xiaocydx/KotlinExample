@file:Suppress("ControlFlowWithEmptyBody", "PrivatePropertyName")

package com.kotlin.example.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * 协程测试例子
 *
 * @author xcc
 * @date 2021/5/15
 */
abstract class CoroutineExample {
    private val TAG = this::class.java.simpleName

    abstract suspend fun run()

    protected val coroutineScope: CoroutineScope =
            CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    protected fun log(msg: Any?) {
        println("$TAG：$msg")
    }
}