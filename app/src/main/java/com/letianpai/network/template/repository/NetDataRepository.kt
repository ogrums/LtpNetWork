package com.letianpai.network.template.repository


import android.content.Context
import com.letianpai.network.model.*
import com.letianpai.network.net.Resource
import com.letianpai.network.template.datasource.RemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext


/**
 * Created by jianbin on 2023-01-29
 */

open class NetDataRepository(context: Context) :
    NetDataRepositorySource {
    private var remoteData: RemoteData
    private var ioDispatcher: CoroutineContext

    init {
        remoteData = RemoteData(context)
        ioDispatcher = Dispatchers.IO
    }

    override fun requestCoopOrgan(): Flow<Resource<List<CoopOrganModel>>> {
        return flow<Resource<List<CoopOrganModel>>> {
            //todo: 这里请求网络
        }.flowOn(ioDispatcher)
    }

    override fun getCaptchaSmsRequest(hashMap: HashMap<String, String>): Flow<Resource<String>> {
        return flow {
            emit(remoteData.getCaptchaSmsRequest(hashMap))
        }.flowOn(ioDispatcher)
    }

    override fun getCaptchaSmsRequest(orderModel: OrderModel): Flow<Resource<String>> {
        return flow {
            emit(remoteData.getCaptchaSmsRequest(orderModel))
        }.flowOn(ioDispatcher)
    }

    override fun getTestRequest(): Flow<Resource<String>> {
        return flow {
            emit(remoteData.getTestRequest())
        }.flowOn(ioDispatcher)
    }

    override fun getOrderModelReq(): Flow<Resource<OrderModel>> {
        return flow {
            emit(remoteData.getOrderModelReq())
        }.flowOn(ioDispatcher)
    }

    //todo: 以下是业务接口
    /**
     * 获取长连三元组
     */
    override fun getIotTriplet(hashMap: HashMap<String, Any>): Flow<Resource<IotTripletM>> {
        return flow {
            emit(remoteData.getIotTriplet(hashMap))
        }.flowOn(ioDispatcher)
    }

    /**
     * 获取sn hardcode
     */
    override fun getSnHardcode(hashMap: HashMap<String, Any>): Flow<Resource<SnHardcode>> {
        return flow {
            emit(remoteData.getSnHardcode(hashMap))
        }.flowOn(ioDispatcher)
    }

    /**
     * 更新升级状态
     */
    override fun upgradeStatus(sn: String,ts: String, model: OtaUpgradeStatusModel): Flow<Resource<GeneModel>> {
        return flow {
            emit(remoteData.upgradeStatus(sn,ts, model))
        }.flowOn(ioDispatcher)
    }

    override  fun getLatestPackage(hashMap: HashMap<String, Any>): Flow<Resource<OtaDataModel>> {
        return flow {
            emit(remoteData.getLatestPackage(hashMap))
        }.flowOn(ioDispatcher)
    }
}
