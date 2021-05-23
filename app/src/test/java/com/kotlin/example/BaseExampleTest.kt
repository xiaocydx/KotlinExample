package com.kotlin.example

import com.kotlin.example.base.visibility.AppSubClass
import com.kotlin.example.base.visibility.AppSubClass2
import org.junit.Test

/**
 * Kotlin基础单元测试
 *
 * @author xcc
 * @date 2021/5/23
 */
class BaseExampleTest {

    /**
     * 验证不同Module下，inline导致[IllegalAccessError]异常
     */
    @Test
    fun differentModuleInlineProtectedExample() {
        AppSubClass().run()
    }

    /**
     * 验证同一Module下，inline不会导致[IllegalAccessError]异常
     */
    @Test
    fun sameModuleInlineProtectedExample() {
        AppSubClass2().run()
    }
}