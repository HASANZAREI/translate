package com.example.translate.models

sealed class BaseModel<out T> {
    data class Success<T>(val data: T): BaseModel<T>()
    data class Error(val message: String): BaseModel<Nothing>()
    object Loading: BaseModel<Nothing>()
}