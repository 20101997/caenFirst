package com.grupposts.data.utils

import com.google.gson.Gson
import com.grupposts.data.models.DefaultResponse
import com.grupposts.data.models.ErrorModel
import com.grupposts.domain.util.EmptyBodyException
import com.grupposts.domain.util.GenericNetworkException
import com.grupposts.domain.util.InvalidDataException
import com.grupposts.domain.util.ServerErrorException
import retrofit2.Response

@Throws(Exception::class)
fun <T> Response<T>.handleResponse(): T {
    if (isSuccessful) {
        body()?.let {
            return it
        } ?: throw EmptyBodyException()
    } else {
        errorBody()?.let {
            val errorJson = Gson().fromJson(it.string(), ErrorModel::class.java)
            throw ServerErrorException(errorJson.message)
        }
        throw GenericNetworkException(message())
    }
}

@Throws(Exception::class)
fun <T> Response<DefaultResponse<T>>.handleDefaultResponse(): T {
    if (isSuccessful) {
        body()?.let {
            it.data?.let { data ->
                return data
            } ?: throw InvalidDataException()
        } ?: throw EmptyBodyException()
    } else {
        errorBody()?.let {
            val errorJson = Gson().fromJson(it.string(), ErrorModel::class.java)
            throw ServerErrorException(errorJson.message)
        }
        throw GenericNetworkException(message())
    }
}