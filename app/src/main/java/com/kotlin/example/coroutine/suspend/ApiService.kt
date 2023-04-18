package com.kotlin.example.coroutine.suspend

import com.kotlin.example.coroutine.User
import kotlinx.coroutines.delay

/**
 * @author xcc
 * @date 2021/8/27
 */
class ApiService {

    suspend fun getUser(): User {
        delay(1000)
        return User("A", 10)
    }
}