package com.kotlin.example.library.visibility

/**
 * @author xcc
 * @date 2021/5/23
 */
abstract class LibrarySuperClass {

    protected fun protectedFunction() {
        println("LibrarySuperClass protectedFunction")
    }

    protected inline fun inlineProtectedFunction() {
        { protectedFunction() }()
        // this::protectedFunction.invoke()
    }
}