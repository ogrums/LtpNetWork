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

//HTTP本身错误码
const val HTTP_INPUT_INVALID: Int = 400
const val HTTP_UNAUTHORIZED = 401
const val HTTP_FORBIDDEN = 403
const val HTTP_NOT_FOUND = 404
const val HTTP_REQUEST_TIMEOUT = 408
const val HTTP_INTERNAL_SERVER_ERROR = 500
const val HTTP_SERVICE_UNAVAILABLE = 503
const val HTTP_GATEWAY_TIMEOUT = 504

//自定义其他错误码
const val UNKNOWN_ERROR = 50000
const val NODATA_ERROR = 20000
const val DATAFORMAT_ERROR = 20001
const val UNAUTHORIZED_ERROR = 20002
const val PARSE_ERROR = 20003

//请求的数据没有权限
const val DATA_UNAUTHORIZED_ERROR = 20004

//请求的数据已经下线
const val DATA_OFFLINE_ERROR = 20005

//数据重复
const val DATA_DUPLICATE_ERROR = 20006

//请求参数无效
const val REQUEST_PARAMS_INVALID = 47001

//APPid  无效
const val APPID_INVALID = 30001

//token 无效
const val USER_TOKEN_INVALID = 40000

//服务器错误
const val SERVER_ERROR = -1
const val SOCKET_TIMEOUT = 40002