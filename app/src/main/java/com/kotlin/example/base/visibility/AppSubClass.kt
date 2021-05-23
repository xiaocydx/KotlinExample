package com.kotlin.example.base.visibility

import com.kotlin.example.library.visibility.LibrarySuperClass

/**
 * @author xcc
 * @date 2021/5/23
 */
class AppSubClass : LibrarySuperClass() {

    override fun run() {
        inlineProtectedFunction()
    }
}