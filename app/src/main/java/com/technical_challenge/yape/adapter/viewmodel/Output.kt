package com.technical_challenge.yape.adapter.viewmodel

sealed class Output<T> {
    data class Loading<T>(val isLoading: Boolean) : Output<T>()
    data class Success<T>(val data: T) : Output<T>()
    data class Failure<T>(val error: Exception, val errorMessage: String) : Output<T>()
}