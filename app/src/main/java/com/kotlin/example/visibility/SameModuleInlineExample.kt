package com.kotlin.example.visibility

import com.kotlin.example.visibility.appsuper.AppSuperClass

/**
 * 同一Module下，inline不会导致[IllegalAccessError]异常
 *
 * @author xcc
 * @date 2021/5/23
 */
class AppSubClass2 : AppSuperClass() {

    fun run() {
        inlineProtectedFunction()
    }
}

fun main() {
    AppSubClass2().run()
}