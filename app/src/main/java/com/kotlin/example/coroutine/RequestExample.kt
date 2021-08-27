package com.kotlin.example.coroutine

import java.util.concurrent.CancellationException
import java.util.concurrent.Executors
import kotlin.random.Random

private val testExecutor = TestExecutor()

fun getUser(callback: Callback<User>): Cancelable {
    return getUser(Result.RANDOM, callback)
}

fun getUser(result: Result, callback: Callback<User>): Cancelable {
    return testExecutor.submit {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            callback.onFailed(CancellationException("Cancelled"))
            return@submit
        }

        val isSucceed = when (result) {
            Result.RANDOM -> Random.nextInt(0, 10) % 2 == 0
            Result.SUCCEED -> true
            Result.FAILED -> false
        }
        if (isSucceed) {
            callback.onSucceed(User("sss", 10))
        } else {
            callback.onFailed(IllegalArgumentException("getUser Failed"))
        }
    }
}

fun shutdown() {
    testExecutor.shutdown()
}

data class User(
    val name: String,
    val age: Int
)

enum class Result {
    RANDOM,
    SUCCEED,
    FAILED
}

interface Callback<T> {

    fun onSucceed(data: T)

    fun onFailed(exception: Throwable)
}

interface Cancelable {

    fun cancel(): Boolean
}

private class TestExecutor {
    private val executor = Executors.newSingleThreadExecutor { runnable ->
        // 设为守护线程，不影响JVM退出
        Thread(runnable).also { it.isDaemon = true }
    }

    fun submit(task: Runnable): Cancelable {
        val future = executor.submit { task.run() }
        return object : Cancelable {
            override fun cancel(): Boolean {
                return future.cancel(true)
            }
        }
    }

    fun shutdown() {
        executor.shutdown()
    }
}