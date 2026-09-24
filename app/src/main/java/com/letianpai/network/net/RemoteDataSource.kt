package com.letianpai.network.net

import android.content.Context
import android.util.Log
import com.elvishew.xlog.XLog
import com.letianpai.network.common.Network
import com.letianpai.network.error.*
import com.letianpai.network.error.manager.ErrorManager
import com.letianpai.network.error.mapper.ErrorMapper
import com.letianpai.network.model.BaseResultBean
import com.letianpai.network.model.OtaUpgradeStatusModel
import com.squareup.moshi.JsonDataException
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import java.text.ParseException
import kotlin.reflect.KSuspendFunction0
import kotlin.reflect.KSuspendFunction1

/**
 * Created by jianbin
 */

abstract class RemoteDataSource(context: Context) {

//    protected var serviceGenerator: ServiceGenerator = ServiceGenerator.getInstance()
    private var errorManager: ErrorManager = ErrorManager(ErrorMapper(context))
    private var networkConnectivity: Network = Network(context)

    // shared helpers
    protected fun <T> responseError(response: Any?): Resource<T> {
        return when (response) {
            is HSError -> {
                Resource.DataError(response)
            }
            is Int -> {
                val error = errorManager.getError(response)
                Resource.DataError(error)
            }
            else -> {
                Resource.DataError(HSError(code = 0, description = "unexpected response type"))
            }
        }
    }

    /**
     * Request with parameters
     */
    protected suspend fun <T> processCallPramasObj(
        responseCall: KSuspendFunction1<T, Response<BaseResultBean<String>>>,
        obj: T
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(obj)
            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    /**
     * Request with parameters
     */
    protected suspend fun <T> processCallPramasMap(
        responseCall: KSuspendFunction1<HashMap<String, String>, Response<BaseResultBean<T>>>,
        hashMap: HashMap<String, String>
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(hashMap)
            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    /**
     * Request with parameters
     */
//    protected suspend fun <T> processCall2Pramas(
//        responseCall: KSuspendFunction1<HashMap<String, String>, Response<BaseResultBean<T>>>,
//        one: String,
//        hashMap: HashMap<String, String>
//    ): Any? {
//        if (!networkConnectivity.isConnected()) {
//            return NO_INTERNET_CONNECTION
//        }
//        return try {
//            val response = responseCall.invoke(hashMap)
//            handleResponse(response)
//        } catch (e: Exception) {
//            handleException(e)
//        }
//    }

    protected suspend fun <T, R> processCall2PramasMapAndBody(
        responseCall: suspend (HashMap<String, String>, T) -> Response<BaseResultBean<R>>,
        hashMap: HashMap<String, String>,
        obj: T
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(hashMap, obj)
            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    protected suspend fun <T, R> processCall3Pramas(
        responseCall: suspend (String, String, R) -> Response<BaseResultBean<T>>,
        one: String,
        two: String,
        model: R
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(one, two, model)
            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    protected suspend fun <T> processCallResponse(
        responseCall: Response<BaseResultBean<T>>
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            handleResponse(responseCall)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    /**
     * Request with parameters
     */
    protected suspend fun <T> processCallPramasMapAny(
        responseCall: KSuspendFunction1<HashMap<String, Any>, Response<BaseResultBean<T>>>,
        hashMap: HashMap<String, Any>
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(hashMap)
            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    /**
     * Request without parameters
     */
    protected suspend fun <T> processCall(responseCall: KSuspendFunction0<Response<BaseResultBean<T>>>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun <T> handleResponse(response: Response<BaseResultBean<T>>): Any? {
        val responseCode = response.code()
        return if (response.isSuccessful) {
            val resultBean = response.body()
            if (resultBean?.code == 0) {
                if (resultBean.data != null) {
                    resultBean.data
                } else {
                    resultBean.msg
                }
            } else {
                if (resultBean != null) {
                    HSError(code = resultBean.code, description = resultBean.msg)
                } else {
                    HSError(code = 0, description = "request failed")
                }
            }
        } else {
            responseCode
        }
    }

    private fun handleException(e: Throwable): Int {
        XLog.i("RemoteDataSource handleException: " +  Log.getStackTraceString(e))
        e.printStackTrace()
        return when (e) {
            is HttpException -> {
                e.code()
            }
            is JsonDataException, is JSONException, is ParseException -> {
                JSON_DATA_ERROR
            }
            is ConnectException -> {
                CONNECT_ERROR
            }
            is SocketTimeoutException -> {
                SOCKET_TIMEOUT_ERROR
            }
            is UnknownHostException -> {
                UNKNOWN_HOST_ERROR
            }
            is UnknownServiceException -> {
                UNKNOWN_SERVICE_ERROR
            }
            else -> {
                OTHERS_ERROR
            }
        }
    }
}
