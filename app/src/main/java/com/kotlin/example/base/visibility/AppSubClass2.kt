package com.kotlin.example.base.visibility

/**
 * @author xcc
 * @date 2021/5/23
 */
class AppSubClass2 : AppSuperClass() {

    override fun run() {
        inlineProtectedFunction()
    }
}