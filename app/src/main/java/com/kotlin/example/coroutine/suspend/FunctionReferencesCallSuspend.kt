@file:Suppress("REDUNDANT_PROJECTION", "UNCHECKED_CAST")

package com.kotlin.example.coroutine.suspend

import com.kotlin.example.coroutine.User
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

/**
 * 在普通函数中通过函数引用调用挂起函数
 *
 * @author xcc
 * @date 2021/8/27
 */
fun main(): Unit = runBlocking {
    val user: User = suspendCoroutineUninterceptedOrReturn { uCont ->
        functionReferenceCallSuspend(uCont)
    }
    println(user)
}

private fun functionReferenceCallSuspend(continuation: Continuation<in User>): Any? {
    val apiService = ApiService()
    val getUser: (ApiService, Continuation<in User>) -> Any? =
            ApiService::getUser as Function2<ApiService, Continuation<in User>, Any?>
    return getUser(apiService, continuation)
}