@file:Suppress("EXPERIMENTAL_API_USAGE", "ControlFlowWithEmptyBody")

package com.kotlin.example

import com.kotlin.example.coroutine.SafeContinuationExample
import com.kotlin.example.coroutine.shutdown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * 协程例子的单元测试
 *
 * @author xcc
 * @date 2021/5/15
 */
class CoroutineExampleTest {
    private val mainThreadSurrogate: ExecutorCoroutineDispatcher = newSingleThreadContext("main")

    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun safeContinuationExample() = runBlocking {
        SafeContinuationExample().runAwait()
    }

    @After
    fun release() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        shutdown()
    }
}