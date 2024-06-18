package com.example.sportsground.data

/**
 * A generic class that holds a value or an exception
 */

sealed class ResultResponse<out R> {
    data class Success<out T>(val data: T) : ResultResponse<T>()
    data class Error(val exception: Exception) : ResultResponse<Nothing>()
}
fun <T> ResultResponse<T>.successOr(fallback: T): T {
    return (this as? ResultResponse.Success<T>)?.data ?: fallback
}
