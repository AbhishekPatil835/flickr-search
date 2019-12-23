package com.example.flickr.data

import retrofit2.Call

abstract class BaseDataSource {

    protected  fun <T> getResult(call:  () -> Call<T>): Result<T> {

        try {
            val response = call().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error("Network call has failed for a following reason: $message")
    }

}