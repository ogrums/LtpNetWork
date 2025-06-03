package com.letianpai.network.net

import com.letianpai.network.error.HSError

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
        val data: T? = null,
        val error: HSError? = null
) {
    class Success<T>(data: T) : Resource<T>(data, null)
    class Loading<T>(data: T? = null) : Resource<T>(data, null)
    class DataError<T>(error: HSError) : Resource<T>(null, error)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=${error?.code}]"
            is Loading<T> -> "Loading"
        }
    }
}
