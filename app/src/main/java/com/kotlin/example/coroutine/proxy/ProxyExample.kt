package com.kotlin.example.coroutine.proxy

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * 函数执行/挂起时间的日志打印代理例子
 *
 * @author xcc
 * @date 2021/8/28
 */
fun main(): Unit = runBlocking {
    testLogcatCostProxy()
}

private const val TAG = "ProxyExample"

private suspend fun testLogcatCostProxy() {
    val apiService: ApiService = ApiServiceImpl()
    val proxy: ApiService = LogcatCostProxy.create(apiService, TAG)
    proxy.apply {
        superDefaultFunction()
        subDefaultFunction()
        normalFunction()
        notRealSuspendFunction()
        realSuspendFunction()
    }
}

private interface ApiServiceSuper {

    fun superDefaultFunction() {
        (1..1_000_000_00).forEach { }
        println("$TAG：superDefaultFunction return")
    }
}

private interface ApiService : ApiServiceSuper {

    fun subDefaultFunction() {
        (1..1_000_000_00).forEach { }
        println("$TAG：subDefaultFunction return")
    }

    fun normalFunction()

    suspend fun notRealSuspendFunction()

    suspend fun realSuspendFunction()
}

private class ApiServiceImpl : ApiService {

    override fun normalFunction() {
        (1..1_000_000_00).forEach { }
        println("$TAG：normalFunction return")
    }

    override suspend fun notRealSuspendFunction() {
        (1..1_000_000_00).forEach { }
        println("$TAG：notRealSuspendFunction return")
    }

    override suspend fun realSuspendFunction() {
        delay(1000)
        println("$TAG：realSuspendFunction return")
    }
}