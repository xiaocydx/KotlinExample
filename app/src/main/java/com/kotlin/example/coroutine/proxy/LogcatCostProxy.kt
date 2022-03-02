@file:Suppress("UNCHECKED_CAST")

package com.kotlin.example.coroutine.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.intercepted

/**
 * 函数执行/挂起时间的日志打印代理
 *
 * @author xcc
 * @date 2021/8/28
 */
object LogcatCostProxy {

    fun <T> create(
        instance: Any,
        logTag: String = instance::class.java.simpleName
    ): T {
        val clazz = instance::class.java
        validateInterface(clazz)
        return Proxy.newProxyInstance(
            clazz.classLoader,
            clazz.interfaces,
            LogcatCostHandler(instance, logTag)
        ) as T
    }

    private fun validateInterface(clazz: Class<*>) {
        require(clazz.interfaces.isNotEmpty()) {
            "${clazz.simpleName}没有实现接口，无法被代理"
        }
    }
}

private class LogcatCostHandler(
    private val instance: Any,
    private val logTag: String
) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any? {
        val newArgs = newArgs(method, args)
        val startTime = System.currentTimeMillis()
        val outcome = method.invoke(instance, *newArgs)
        if (outcome != COROUTINE_SUSPENDED) {
            // method为普通函数或者挂起函数没有真正挂起，此时为endTime的位置
            logcat(startTime, method)
        }
        return outcome
    }

    private fun newArgs(method: Method, args: Array<Any>?): Array<Any> {
        val safeArgs = args ?: emptyArray()
        val lastArg = safeArgs.lastOrNull()
        if (lastArg is Continuation<*> && method.returnType == Object::class.java) {
            // method为挂起函数，对continuation对象做一层类代理
            safeArgs[safeArgs.lastIndex] = ContinuationWrapper(method, lastArg.intercepted())
        }
        return safeArgs
    }

    private fun logcat(startTime: Long, method: Method) {
        val time = System.currentTimeMillis() - startTime
        println("$logTag：${instance::class.java.simpleName}.${method.name} --> ${time}ms")
    }

    private inner class ContinuationWrapper<T>(
        private val method: Method,
        private val delegate: Continuation<T>
    ) : Continuation<T> by delegate {
        private val startTime = System.currentTimeMillis()

        override fun resumeWith(result: Result<T>) {
            // 挂起函数的endTime位置
            logcat(startTime, method)
            delegate.resumeWith(result)
        }
    }
}