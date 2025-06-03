package com.letianpai.network.error.mapper

import android.content.Context
import com.letianpai.network.error.*


class ErrorMapper constructor(val context: Context?) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String? {
        return context?.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, "Please check your internet connection"),
            Pair(NETWORK_ERROR, "Network error, could get data,please try again!"),
            Pair(PASS_WORD_ERROR, "Password must be >4 characters"),
            Pair(USER_NAME_ERROR, "Not a valid username"),
            Pair(JSON_DATA_ERROR, "json error --"),
            Pair(CONNECT_ERROR, "connect error --"),
            Pair(SOCKET_TIMEOUT_ERROR, "time out error --"),
            Pair(UNKNOWN_HOST_ERROR, "unknown host error --"),
            Pair(UNKNOWN_SERVICE_ERROR, "unknown service error --"),
            Pair(OTHERS_ERROR, "others error --"),
            Pair(HTTP_INPUT_INVALID, "请求参数错误"),
            Pair(HTTP_UNAUTHORIZED, "401 认证拦截"),
            Pair(HTTP_FORBIDDEN, "403 账号或密码错误"),
            Pair(HTTP_NOT_FOUND, "404 请求地址错误"),
            Pair(HTTP_REQUEST_TIMEOUT, "请求超时"),
            Pair(HTTP_SERVICE_UNAVAILABLE, "服务器错误，请检查网络或稍后重试"),
            Pair(HTTP_INTERNAL_SERVER_ERROR, "服务器错误，请检查网络或稍后重试"),
            Pair(HTTP_GATEWAY_TIMEOUT, "服务器错误，请检查网络或稍后重试"),
        ).withDefault { "Network error, could get data,please try again!" }
}
