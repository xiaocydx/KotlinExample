package com.kotlin.example.coroutine.scope

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 创建安全的应用级别CoroutineScope
 *
 * @author xcc
 * @date 2021/8/27
 */
fun main() {
    val job = SupervisorJob().toDisallowCancel("不允许调用ApplicationScope的cancel()")
    val applicationScope = CoroutineScope(job + Dispatchers.Default)
    applicationScope.cancel()
}

fun Job.toDisallowCancel(
    reason: String = "不允许调用cancel()"
): Job = DisallowCancelJob(this, reason)

internal class DisallowCancelJob(
    delegate: Job,
    private val reason: String
) : Job by delegate, CoroutineContext.Element { // 这里实现CoroutineContext.Element只是为了加强重写提示

    //region 需要重写CoroutineContext.Element下的函数，避免将上下文元素合并逻辑的实现委托给delegate
    override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? {
        return super<Job>.get(key)
    }

    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
        return super<Job>.fold(initial, operation)
    }

    override fun plus(context: CoroutineContext): CoroutineContext {
        return super<Job>.plus(context)
    }

    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
        return super<Job>.minusKey(key)
    }
    //endregion

    override fun cancel(cause: CancellationException?) {
        throw IllegalStateException(reason)
    }

    override fun cancel(cause: Throwable?): Boolean {
        throw IllegalStateException(reason)
    }

    override fun cancel() {
        throw IllegalStateException(reason)
    }
}