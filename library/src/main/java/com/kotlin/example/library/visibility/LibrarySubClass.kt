package com.kotlin.example.library.visibility

/**
 * @author xcc
 * @date 2021/5/23
 */
class LibrarySubClass : LibrarySuperClass() {

    fun run() {
        inlineProtectedFunction()
    }
}