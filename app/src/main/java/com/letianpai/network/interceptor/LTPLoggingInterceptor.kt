package com.letianpai.network.interceptor

import android.util.Log
import com.elvishew.xlog.XLog
import okhttp3.Interceptor
import okhttp3.Response

class LTPLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        var message: String
        val requestMethod = request.method
        message = if (requestMethod == "POST") {
            String.format(
                "request %s on %s %n %s %s",
                request.url, request.body, chain.connection(), request.headers
            )
        } else {
            String.format(
                "request %s : %n :%s : %s",
                request.url, chain.connection(), request.headers
            )
        }

        XLog.i(message)

        return chain.proceed(request)
    }
}