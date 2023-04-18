package com.kotlin.example.coroutine.suspend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kotlin.example.coroutine.User;

import kotlin.coroutines.Continuation;

/**
 * @author xcc
 * @date 2021/8/27
 */
public class BridgeMethod {

    @Nullable
    public static Object getUser(@NonNull final ApiService apiService,
                                 @NonNull final Continuation<? super User> continuation) {
        return apiService.getUser(continuation);
    }
}