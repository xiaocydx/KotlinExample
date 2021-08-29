@file:Suppress("REDUNDANT_PROJECTION")

package com.kotlin.example.coroutine.suspend

import com.kotlin.example.coroutine.User
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

/**
 * 在普通函数中通过Java方法调用挂起函数
 *
 * @author xcc
 * @date 2021/8/27
 */
fun main(): Unit = runBlocking {
    val user: User = suspendCoroutineUninterceptedOrReturn { uCont ->
        bridgeMethodCallSuspend(uCont)
    }
    println(user)
}

private fun bridgeMethodCallSuspend(continuation: Continuation<in User>): Any? {
    val apiService = ApiService()
    return BridgeMethod.getUser(apiService, continuation)
}