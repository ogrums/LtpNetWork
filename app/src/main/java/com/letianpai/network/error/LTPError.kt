package com.letianpai.network.error

/**
 * Created by jianbin
 */

data class HSError(val code: Int, val description: String) {
    constructor(exception: Exception) : this(
        code = DEFAULT_ERROR, description = exception.message ?: ""
    )
}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3
const val JSON_DATA_ERROR = -4
const val CONNECT_ERROR = -5
const val SOCKET_TIMEOUT_ERROR = -6
const val UNKNOWN_HOST_ERROR = -7
const val OTHERS_ERROR = -8
const val UNKNOWN_SERVICE_ERROR = -9
const val PASS_WORD_ERROR = -101
const val USER_NAME_ERROR = -102
const val USER_PHONE_ERROR = -102
const val CHECK_YOUR_PRIVACY = -104

// HTTP status codes
const val HTTP_INPUT_INVALID: Int = 400
const val HTTP_UNAUTHORIZED = 401
const val HTTP_FORBIDDEN = 403
const val HTTP_NOT_FOUND = 404
const val HTTP_REQUEST_TIMEOUT = 408
const val HTTP_INTERNAL_SERVER_ERROR = 500
const val HTTP_SERVICE_UNAVAILABLE = 503
const val HTTP_GATEWAY_TIMEOUT = 504

// Other custom error codes
const val UNKNOWN_ERROR = 50000
const val NODATA_ERROR = 20000
const val DATAFORMAT_ERROR = 20001
const val UNAUTHORIZED_ERROR = 20002
const val PARSE_ERROR = 20003

// The requested data is not authorized
const val DATA_UNAUTHORIZED_ERROR = 20004

// The requested data is offline
const val DATA_OFFLINE_ERROR = 20005

// Duplicate data
const val DATA_DUPLICATE_ERROR = 20006

// Invalid request parameters
const val REQUEST_PARAMS_INVALID = 47001

// Invalid app id
const val APPID_INVALID = 30001

// Invalid token
const val USER_TOKEN_INVALID = 40000

// Server error
const val SERVER_ERROR = -1
const val SOCKET_TIMEOUT = 40002