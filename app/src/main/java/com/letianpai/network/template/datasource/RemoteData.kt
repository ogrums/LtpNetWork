package com.letianpai.network.template.datasource


import android.content.Context
import com.letianpai.network.model.*
import com.letianpai.network.net.Resource
import com.letianpai.network.net.RemoteDataSource
import com.letianpai.network.net.ServiceGenerator


/**
 * created by yujianbin on 2023-01-19
 */
open class RemoteData(context: Context) :
    RemoteDataSource(context) {
    private var newApiService: NewApi = ServiceGenerator().createService(NewApi::class.java)

    suspend fun getCaptchaSmsRequest(order: OrderModel): Resource<String> {
        val response =
            processCallPramasObj(newApiService::getCaptchaSmsRequest, order)
        return if (response is String) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }


    suspend fun getCaptchaSmsRequest(hashMap: HashMap<String, String>): Resource<String> {
        val response =
            processCallPramasMap(newApiService::getCaptchaSmsRequestMap, hashMap)
        return if (response is String) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }

    suspend fun getTestRequest(): Resource<String> {
        val response = processCall(newApiService::getTestRequest)
        return if (response is String) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }


    suspend fun getOrderModelReq(): Resource<OrderModel> {
        val response = processCall(newApiService::getOrderModelReq)
        return if (response is OrderModel) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }
    //----------------TODO：以下是业务接口--------

    /**
     * 获取长连三元组
     */
    suspend fun getIotTriplet(hashMap: HashMap<String, Any>): Resource<IotTripletM>{
        val response =
            processCallPramasMapAny(newApiService::getIotTriplet, hashMap)
        return if (response is IotTripletM) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }

    suspend fun getSnHardcode(hashMap: HashMap<String, Any>): Resource<SnHardcode>{
        val response =
            processCallPramasMapAny(newApiService::getSnHardcode, hashMap)
        return if (response is SnHardcode) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }

    suspend fun upgradeStatus(sn: String, ts: String,  model: OtaUpgradeStatusModel): Resource<GeneModel>{
        val response =
            processCall3Pramas(newApiService::upgradeStatus, sn, ts, model)
        return if (response is GeneModel) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }

    suspend fun getLatestPackage(hashMap: HashMap<String, Any>): Resource<OtaDataModel>{
        val response =
            processCallPramasMapAny(newApiService::getLatestPackage, hashMap)
        return if (response is OtaDataModel) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }
}
