@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.kotlin.example.coroutine.launch

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 添加启动协程的扩展函数
 *
 * @author xcc
 * @date 2021/8/28
 */
fun main() {
    // launchExample()
    launchCoroutineExample()
}

/**
 * 通过扩展函数[CoroutineScope.launch]启动协程
 */
private fun launchExample() {
    GlobalScope.launch {
        val deferred: Deferred<Any> = async {
            throw IllegalArgumentException("async throw exception")
        }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("try catch await：$e")
            return@launch
        }
    }
    while (true) {
    }
}

/**
 * 通过扩展函数[CoroutineScope.launchCoroutine]启动协程
 */
private fun launchCoroutineExample() {
    GlobalScope.launchCoroutine {
        val deferred: Deferred<Any> = async {
            throw IllegalArgumentException("async throw exception")
        }
        kotlin.runCatching {  }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("try catch await：$e")
            return@launchCoroutine
        }
    }.invokeOnException {
        println("invokeOnException：$it")
    }
    while (true) {
    }
}

/**
 * 出现异常时调用[handler]
 *
 * 需要过滤掉[CancellationException],
 * 因为[CancellationException]和Thread的[InterruptedException]类似，是一种取消信号，并不是程序代码出问题的原因，
 * 这也是[CoroutineExceptionHandler.handleException]不会收到[CancellationException]的原因。
 */
inline fun Job.invokeOnException(
    crossinline handler: (exception: Throwable) -> Unit
): DisposableHandle = invokeOnCompletion { exception ->
    exception?.takeIf { it !is CancellationException }?.let(handler)
}

fun CoroutineScope.launchCoroutine(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val newContext = combineCoroutineContext(context)
    return launch(newContext, start, block)
}

/**
 * 合并上下文
 *
 * 要将[DefaultCoroutineExceptionHandler]要放在前面，
 * 若[context]包含[CoroutineExceptionHandler]上下文元素，
 * 则可以将[DefaultCoroutineExceptionHandler]替换掉。
 */
private fun combineCoroutineContext(context: CoroutineContext): CoroutineContext {
    return DefaultCoroutineExceptionHandler + context
}

internal object DefaultCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

    /**
     * 仅Debug环境下显示Toast、异常堆栈信息，确保开发过程中及时发现问题并解决。
     * ```
     * if (isDebug) {
     *     showToast(errorMessage)
     *     exception.printStackTrace()
     * }
     * ```
     */
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("DefaultCoroutineExceptionHandler：$exception")
    }
}