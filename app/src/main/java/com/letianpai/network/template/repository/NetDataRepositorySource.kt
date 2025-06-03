package com.letianpai.network.template.repository

import com.letianpai.network.model.*
import com.letianpai.network.net.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by jianbin on 2023-01-28
 */

interface NetDataRepositorySource {

    //TODO: 下面接口全部为测试
    fun requestCoopOrgan(): Flow<Resource<List<CoopOrganModel>>>
    fun getCaptchaSmsRequest(hashMap: HashMap<String, String>): Flow<Resource<String>>
    fun getCaptchaSmsRequest(orderModel: OrderModel): Flow<Resource<String>>
    fun getTestRequest(): Flow<Resource<String>>
    fun getOrderModelReq(): Flow<Resource<OrderModel>>


    //TODO:  以下是 业务接口
    fun getIotTriplet(hashMap: HashMap<String, Any>): Flow<Resource<IotTripletM>>
    fun getSnHardcode(hashMap: HashMap<String, Any>): Flow<Resource<SnHardcode>>
    fun upgradeStatus(sn: String ,ts: String, model: OtaUpgradeStatusModel): Flow<Resource<GeneModel>>
    fun getLatestPackage(hashMap: HashMap<String, Any>): Flow<Resource<OtaDataModel>>


}
