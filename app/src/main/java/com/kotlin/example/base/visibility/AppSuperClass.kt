package com.kotlin.example.base.visibility

import com.kotlin.example.library.TestExample

/**
 * @author xcc
 * @date 2021/5/23
 */
abstract class AppSuperClass : TestExample() {

    protected fun protectedFunction() {
        log("call protectedFunction")
    }

    protected inline fun inlineProtectedFunction() {
        { protectedFunction() }()
        // this::protectedFunction.invoke()
    }
}