package com.example.patientrecord.repository

import com.example.patientrecord.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is retrofit2.HttpException -> {
                        Resource.Failure(
                            isNetworkError = false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }

                    else -> {
                        Resource.Failure(isNetworkError = true, null, null)
                    }
                }
            }
        }
    }
}