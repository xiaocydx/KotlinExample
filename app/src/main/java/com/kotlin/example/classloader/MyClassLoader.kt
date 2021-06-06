package com.kotlin.example.classloader

import java.io.IOException

/**
 * @author xcc
 * @date 2021/5/30
 */
class MyClassLoader : ClassLoader() {

    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String): Class<*> {
        try {
            val fileName = "${name.substringAfterLast(".")}.class"
            val inputStream =
                    this::class.java.getResourceAsStream(fileName) ?: return super.loadClass(name)
            val b = ByteArray(inputStream.available())
            inputStream.read(b)
            return defineClass(name, b, 0, b.size)
        } catch (e: IOException) {
            throw ClassNotFoundException(name)
        }
    }
}