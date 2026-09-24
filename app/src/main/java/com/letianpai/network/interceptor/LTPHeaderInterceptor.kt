package com.letianpai.network.interceptor


import android.util.Log
import com.letianpai.network.common.HeaderUtils
import com.letianpai.network.common.SystemProperties
import com.letianpai.network.common.SystemPropertiesProxy
import com.letianpai.network.net.GLOBAL_IotUrl
import com.letianpai.network.net.RELEASE_HOST_TEST
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * created by yujianbin on 2019/3/21
 *
 */
private const val contentType = "Content-Type"
private const val contentTypeValue = "application/json"

class YHeaderInterceptor: Interceptor {
    private val partSecretKey = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        // when the MAC address is present
        val macAddress = original.url.queryParameter("mac")
        val sn = original.url.queryParameter("sn")
        if (sn != null){
            return chain.proceed(getSnSignBuild(original))
        }
        if (macAddress != null) {
            return chain.proceed(getMacSignBuild(original))
        }
        return chain.proceed(original.newBuilder().build())
    }

    private fun getMacSignBuild(original: Request): Request {
        val macAddress = original.url.queryParameter("mac")
        val timestamp = original.url.queryParameter("ts")
        val deviceSecretKey = HeaderUtils.md5(macAddress + timestamp + partSecretKey)
        val macSign = sha256(macAddress + timestamp + deviceSecretKey)

        return original.newBuilder()
            .header(contentType, contentTypeValue)
            .header("Authorization", "Bearer $macSign")
            .header("country", getCountry())
            .method(original.method, original.body)
            .build()
    }

    private fun getSnSignBuild(original: Request): Request {
        val sn = original.url.queryParameter("sn")
        val ts = original.url.queryParameter("ts")
        val hardcode = SystemProperties.get("persist.sys.hardcode")
        val deviceSecretKey = HeaderUtils.md5(sn+ hardcode + ts + partSecretKey)
        val snSign = HeaderUtils.sha256(sn + hardcode + ts + deviceSecretKey)
//        Log.i("<<<", "--hardcode=${hardcode}")
//        Log.i("<<<", "--snSign=${snSign}")
//        Log.i("<<<", "--deviceSecretKey=${deviceSecretKey}")
        return original.newBuilder()
            .header(contentType, contentTypeValue)
            .header("Authorization", "Bearer $snSign")
            .header("country", getCountry())
            .method(original.method, original.body)
            .build()

    }


    private fun sha256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(input.toByteArray(StandardCharsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun getCountry(): String{
        val language = SystemProperties.get("persist.sys.region.language")
        if (language != "zh"){
            return "global"
        }else{
            return "cn"
        }
    }
}
