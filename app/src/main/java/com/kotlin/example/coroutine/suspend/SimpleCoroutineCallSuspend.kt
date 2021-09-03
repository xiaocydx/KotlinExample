@file:Suppress("REDUNDANT_PROJECTION")

package com.kotlin.example.coroutine.suspend

import com.kotlin.example.coroutine.User
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.startCoroutineUninterceptedOrReturn

/**
 * 在普通函数中通过标准库的简单协程调用挂起函数
 *
 * @author xcc
 * @date 2021/8/27
 */
fun main(): Unit = runBlocking {
    var user: User = suspendCancellableCoroutine { continuation ->
        simpleCoroutineCallSuspend(continuation)
    }
    println(user)

    user = withContext(CustomDispatcher()) {
        suspendCancellableCoroutine { continuation ->
            simpleCoroutineCallSuspend(continuation)
        }
    }
    println(user)
}

private fun simpleCoroutineCallSuspend(continuation: Continuation<in User>): Any? {
    val apiService = ApiService()
    val start = System.currentTimeMillis()
    return suspend {
        println("start ${System.currentTimeMillis() - start}ms")
        apiService.getUser()
    }.startCoroutineUninterceptedOrReturn(continuation)

    // import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
    // 启动协程会进行调度
    // suspend {
    //     println("start ${System.currentTimeMillis() - start}ms")
    //     apiService.getUser()
    // }.startCoroutine(continuation)
    // return COROUTINE_SUSPENDED
}

private class CustomDispatcher : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*> = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatchedContinuation(continuation)
    }
}

private class DispatchedContinuation<T>(
    private val continuation: Continuation<T>
) : Continuation<T> by continuation {
    private val executor = Executors.newSingleThreadExecutor { runnable ->
        // 设为守护线程，不影响JVM退出
        Thread(runnable).also { it.isDaemon = true }
    }

    override fun resumeWith(result: Result<T>) {
        executor.execute {
            Thread.sleep(1000)
            continuation.resumeWith(result)
        }
    }
}