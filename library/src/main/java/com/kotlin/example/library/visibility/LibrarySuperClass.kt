package com.kotlin.example.library.visibility

import com.kotlin.example.library.TestExample

/**
 * @author xcc
 * @date 2021/5/23
 */
abstract class LibrarySuperClass : TestExample() {

    protected fun protectedFunction() {
        log("call protectedFunction")
    }

    protected inline fun inlineProtectedFunction() {
        { protectedFunction() }()
        // this::protectedFunction.invoke()
    }
}