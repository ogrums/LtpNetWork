package com.letianpai.network.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * created by yujianbin on 2019/3/21
 * Shared query parameters
 */
class LTPQueryParameterIntercepter : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val request: Request
        var modifiedUrl: HttpUrl? = null

//        modifiedUrl = if (BuildConfig.DEBUG){
//            originalRequest.url().newBuilder()
//                    .addQueryParameter(Contents.HEADER.AK, UrlHelper.DEV_ACCESS_KEY)
//                    .build()
//        }else{
//            originalRequest.url().newBuilder()
//                    .addQueryParameter(Contents.HEADER.AK, UrlHelper.PRO_ACCESS_KEY)
//                    .build()
//        }
//
//        request = originalRequest.newBuilder().url(modifiedUrl).build()

        return chain.proceed(originalRequest)
    }
}