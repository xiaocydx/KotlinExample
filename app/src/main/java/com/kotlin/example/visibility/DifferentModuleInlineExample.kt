package com.kotlin.example.visibility

import com.kotlin.example.library.visibility.LibrarySuperClass

/**
 * 不同Module下，inline导致[IllegalAccessError]异常
 *
 * @author xcc
 * @date 2021/5/23
 */
class AppSubClass : LibrarySuperClass() {

    fun run() {
        inlineProtectedFunction()
    }
}

fun main() {
    AppSubClass().run()
}