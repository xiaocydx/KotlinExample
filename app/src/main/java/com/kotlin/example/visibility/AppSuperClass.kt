package com.kotlin.example.visibility

/**
 * @author xcc
 * @date 2021/5/23
 */
abstract class AppSuperClass {

    protected fun protectedFunction() {
        println("AppSuperClass protectedFunction")
    }

    protected inline fun inlineProtectedFunction() {
        { protectedFunction() }()
        // this::protectedFunction.invoke()
    }
}