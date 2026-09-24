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
            Pair(HTTP_INPUT_INVALID, "invalid request parameters"),
            Pair(HTTP_UNAUTHORIZED, "401 authentication rejected"),
            Pair(HTTP_FORBIDDEN, "403 wrong account or password"),
            Pair(HTTP_NOT_FOUND, "404 unknown address"),
            Pair(HTTP_REQUEST_TIMEOUT, "request timed out"),
            Pair(HTTP_SERVICE_UNAVAILABLE, "server error, check the network or retry later"),
            Pair(HTTP_INTERNAL_SERVER_ERROR, "server error, check the network or retry later"),
            Pair(HTTP_GATEWAY_TIMEOUT, "server error, check the network or retry later"),
        ).withDefault { "Network error, could get data,please try again!" }
}
