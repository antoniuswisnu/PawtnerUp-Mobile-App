package com.example.pawtnerup.di

sealed class ResultState<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val data: String) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}