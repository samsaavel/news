package com.example.testapplication.data

sealed class ApiResult<out SUCCESS_TYPE : Any> {
    data class Success<out SUCCESS_TYPE : Any>(
        val data: SUCCESS_TYPE
    ) : ApiResult<SUCCESS_TYPE>()

    object Failure : ApiResult<Nothing>()
}