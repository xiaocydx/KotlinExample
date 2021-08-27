package com.kotlin.example.coroutine

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * SafeContinuation测试例子
 *
 * @author xcc
 * @date 2021/5/15
 */
class SafeContinuationExample : CoroutineExample() {

    override suspend fun run() {
        val user = getUserSuspend()
        // val user = getUserSuspendImmediately()
        // val user = getUserSuspendWithoutSafe()
        log("user = $user")
    }

    /**
     * 通过[suspendCoroutine]将传入回调的函数转为挂起函数，
     * 可以尝试多次调用[Continuation.resumeWith]，SafeContinuation内部会抛出异常。
     */
    private suspend fun getUserSuspend(): User = suspendCoroutine { continuation ->
        getUser(object : Callback<User> {
            override fun onSucceed(data: User) {
                continuation.resume(data)
                // 再次resume会抛出异常提示「Already resumed」
                // continuation.resume(data)
            }

            override fun onFailed(exception: Throwable) {
                continuation.resumeWithException(exception)
            }
        })
    }

    /**
     * 直接传入结果执行挂起函数恢复操作的场景
     *
     * SafeContinuation处理后的伪代码逻辑：
     * ```
     * private suspend fun getUserSuspendImmediately(): User {
     *      return User("immediately", 10)
     *      // throw IllegalArgumentException("immediately")
     * }
     * ```
     */
    private suspend fun getUserSuspendImmediately(): User = suspendCoroutine { continuation ->
        continuation.resume(User("immediately", 10))
        // continuation.resumeWithException(IllegalArgumentException("immediately"))
    }

    /**
     * 通过[suspendCoroutineUninterceptedOrReturn]将传入回调的函数转为挂起函数，
     * 可以尝试多次调用[Continuation.resumeWith]，后续的代码会被执行多次。
     */
    private suspend fun getUserSuspendWithoutSafe(): User {
        return suspendCoroutineUninterceptedOrReturn { continuation ->
            getUser(object : Callback<User> {
                override fun onSucceed(data: User) {
                    continuation.resume(data)
                    // 再次resume会导致后续的代码再执行一次
                    continuation.resume(data)
                }

                override fun onFailed(exception: Throwable) {
                    continuation.resumeWithException(exception)
                }
            })
            // 返回挂起标志位，表示真正的挂起
            COROUTINE_SUSPENDED
        }
    }
}

fun main(): Unit = runBlocking {
    SafeContinuationExample().run()
}

