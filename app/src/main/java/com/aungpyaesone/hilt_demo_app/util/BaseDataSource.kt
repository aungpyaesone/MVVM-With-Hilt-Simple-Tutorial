package com.aungpyaesone.hilt_demo_app.util

import retrofit2.Response
import java.lang.Exception

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call:suspend () ->Response<T>) : Resource<T>{
        try {
            val response = call()
            if(response.isSuccessful){
                val body = response.body()
                body?.let {
                   return Resource.success(it)
                }
            }
            return error("${response.code()} ${response.message()}")
        }
        catch (e: Exception){
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(message:String):Resource<T>{
        return Resource.error("Network call has failed for a following reason: $message")
    }
}