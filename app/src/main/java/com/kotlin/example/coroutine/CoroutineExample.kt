@file:Suppress("ControlFlowWithEmptyBody")

package com.kotlin.example.coroutine

import com.kotlin.example.TestExample
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * 协程测试例子
 *
 * @author xcc
 * @date 2021/5/15
 */
abstract class CoroutineExample : TestExample() {

    protected val coroutineScope: CoroutineScope =
            CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Deprecated("runAwait代替")
    final override fun run() {
    }

    abstract suspend fun runAwait()
}