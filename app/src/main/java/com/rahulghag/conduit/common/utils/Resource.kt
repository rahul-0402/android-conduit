package com.rahulghag.conduit.common.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: UiMessage? = null,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null, message: UiMessage) : Resource<T>(data, message)
}
