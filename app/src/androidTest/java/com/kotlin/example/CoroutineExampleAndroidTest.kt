@file:Suppress("ControlFlowWithEmptyBody")

package com.kotlin.example

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kotlin.example.coroutine.SafeContinuationExample
import com.kotlin.example.coroutine.shutdown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 协程测试例子
 *
 * **注意**：与[CoroutineExampleTest]的区别在于，
 * 该测试例子跑的是Android环境，因此主线程调度器是Android的主线程。
 *
 * @author xcc
 * @date 2021/5/15
 */
@RunWith(AndroidJUnit4::class)
class CoroutineExampleAndroidTest {

    @Test
    fun safeContinuationExample() = runBlocking(Dispatchers.Main) {
        SafeContinuationExample().run()
    }

    @After
    fun release() {
        shutdown()
    }
}