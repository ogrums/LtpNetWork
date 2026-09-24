package com.letianpai.network.net


import android.util.Log
import com.letianpai.network.BuildConfig
import com.letianpai.network.common.SystemProperties
import com.letianpai.network.interceptor.LTPLoggingInterceptor
import com.letianpai.network.interceptor.YHeaderInterceptor
import com.letianpai.network.moshiFactories.MyKotlinJsonAdapterFactory
import com.letianpai.network.moshiFactories.MyStandardJsonAdapters
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * created by yujianbin on 2023-01-19
 */

class ServiceGenerator {
    private val timeoutRead = 30   //In seconds
    private val timeoutConnect = 30   //In seconds

    // Do not cache a singleton: the host must change when the language changes.
//    companion object{
//        fun getInstance()= InstanceHelper.serviceGenerator
//    }
//
//    object InstanceHelper{
//        val serviceGenerator = ServiceGenerator()
//    }

    private val retrofit: Retrofit

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(YHeaderInterceptor())
            .addInterceptor(LTPLoggingInterceptor())
            .addInterceptor(logger)
            .connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        retrofit = Retrofit.Builder()
                .baseUrl(getBaseUrl()).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
                .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    private fun getMoshi(): Moshi {
        return Moshi.Builder()
                .add(MyKotlinJsonAdapterFactory())
                .add(MyStandardJsonAdapters.FACTORY)
                .build()
    }

    private fun getBaseUrl(): String{
        val language:String? = SystemProperties.get("persist.sys.region.language")
        return if (language!=null && language.isNotEmpty()){
            if (language != "zh"){
                GLOBAL_IotUrl
            }else{
                RELEASE_HOST_TEST
            }
        }else{
            RELEASE_HOST_TEST
        }
    }
}
