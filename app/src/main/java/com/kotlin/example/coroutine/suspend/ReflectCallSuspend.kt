@file:Suppress("REDUNDANT_PROJECTION")

package com.kotlin.example.coroutine.suspend

import com.kotlin.example.coroutine.User
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.system.measureTimeMillis

/**
 * 在普通函数中通过反射调用挂起函数
 *
 * @author xcc
 * @date 2021/8/27
 */
fun main(): Unit = runBlocking {
    var user: User = suspendCancellableCoroutine { continuation ->
        javaReflectCallSuspend(continuation)
    }
    println(user)

    user = suspendCancellableCoroutine { continuation ->
        kotlinReflectCallSuspend(continuation)
    }
    println(user)
}

/**
 * 在普通函数中通过Java反射调用挂起函数
 */
private fun javaReflectCallSuspend(continuation: Continuation<in User>): Any? {
    val apiService = ApiService()
    val outcome: Any?
    val time = measureTimeMillis {
        val getUser = apiService::class.java
                .getDeclaredMethod("getUser", Continuation::class.java)
        outcome = getUser.invoke(apiService, continuation)
    }
    println("Java reflect：${time}ms")
    return outcome
}

/**
 * 在普通函数中通过Kotlin反射调用挂起函数
 */
private fun kotlinReflectCallSuspend(continuation: Continuation<in User>): Any? {
    val apiService = ApiService()
    val outcome: Any?
    // 此处改用measureTimedValue会抛出类型转换错误
    val time = measureTimeMillis {
        outcome = apiService::getUser.call(continuation)
    }
    println("Kotlin first reflect：${time}ms")
    return outcome
}