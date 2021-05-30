package com.kotlin.example.base.visibility

/**
 * 同一Module下，inline不会导致[IllegalAccessError]异常
 *
 * @author xcc
 * @date 2021/5/23
 */
fun main() {
    AppSubClass2().run()
}

class AppSubClass2 : AppSuperClass() {

    fun run() {
        inlineProtectedFunction()
    }
}