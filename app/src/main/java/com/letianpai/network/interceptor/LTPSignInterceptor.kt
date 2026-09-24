package com.letianpai.network.interceptor

import android.util.Log
import com.letianpai.network.common.Contents
import com.letianpai.network.common.HeaderUtils
import com.letianpai.network.common.StringUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * created by yujianbin on 2019/3/21
 *
 */
class LTPSignInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        val origin_request = chain.request()
        var __sign: String?
        val timestamp = java.lang.Long.toString(System.currentTimeMillis() / 1000)

        // TreeMap sorts entries by key
        val param_map = TreeMap<String, String>()

        val sign = StringBuilder()
        if (origin_request.method == "POST") {
            /* FormBody body = (FormBody) origin_request.body();
                    if(body.size() != 0){
                        for(int i =0; i<body.size(); i++){
                            param_map.put(body.encodedName(i),body.encodedValue(i));
                        }
                    }*/
        } else if (origin_request.method == "GET" || origin_request.method.equals("DELETE")) {
            // append the timestamp
            val url = origin_request.url.toString()

            Log.d("BSignInterceptor", "url：： $url")

            // read the parameter list
            val parts = url.split("?")
            if (parts.size > 1) {
                // read each parameter pair
                val param_pairs = parts[1].split("&")
                Log.d("BSignInterceptor", "param_pairs：： $param_pairs")

                for (pair in param_pairs) {
                    val param = pair.split("=")
                    if (param.size != 2) continue

                    Log.d("BSignInterceptor", "param_pairs：：" + param[0] + "--->" + param[1])
                    param_map.put(param[0], StringUtils.unescape(param[1]))
                }
            }
        }

        //ak
//        if (BuildConfig.DEBUG) {
//            param_map["ak"] = UrlHelper.DEV_ACCESS_KEY
//        } else {
//            param_map["ak"] = UrlHelper.PRO_ACCESS_KEY
//        }

        param_map["time"] = timestamp

        val it = param_map.keys.iterator()
        // join the parameters
        while (it.hasNext()) {
            val key = it.next()
            val value = param_map.get(key)
            sign.append(key + value)
        }

        //sk
//        if (BuildConfig.DEBUG) {
//            sign.append(UrlHelper.DEV_SECRET_KEY)
//        } else {
//            sign.append(UrlHelper.PRO_SECRET_KEY)
//        }

        Log.d("BSignInterceptor", "sign：： $sign")

        // MD5
        __sign = HeaderUtils.md5(sign.toString())
        val requestBuilder = origin_request.newBuilder()
                .addHeader(Contents.HEADER.SIGN, __sign)
                .addHeader(Contents.HEADER.TIME, timestamp)
                .method(origin_request.method, origin_request.body)
        val newRequest = requestBuilder.build()

        return chain.proceed(newRequest)
    }
}