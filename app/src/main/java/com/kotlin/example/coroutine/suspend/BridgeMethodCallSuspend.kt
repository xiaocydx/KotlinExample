@file:Suppress("REDUNDANT_PROJECTION")

package com.kotlin.example.coroutine.suspend

import com.kotlin.example.coroutine.User
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation

/**
 * 在普通函数中通过Java方法调用挂起函数
 *
 * @author xcc
 * @date 2021/8/27
 */
fun main(): Unit = runBlocking {
    val user: User = suspendCancellableCoroutine { continuation ->
        bridgeMethodCallSuspend(continuation)
    }
    println(user)
}

private fun bridgeMethodCallSuspend(continuation: Continuation<in User>): Any? {
    val apiService = ApiService()
    return BridgeMethod.getUser(apiService, continuation)
}