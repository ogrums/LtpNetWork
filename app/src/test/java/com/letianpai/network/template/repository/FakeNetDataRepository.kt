package com.letianpai.network.template.repository

import com.letianpai.network.model.CoopOrganModel
import com.letianpai.network.model.GeneModel
import com.letianpai.network.model.IotTripletM
import com.letianpai.network.model.OrderModel
import com.letianpai.network.model.OtaDataModel
import com.letianpai.network.model.OtaUpgradeStatusModel
import com.letianpai.network.model.PackageCollectionModel
import com.letianpai.network.model.PackageModel
import com.letianpai.network.model.SnHardcode
import com.letianpai.network.net.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * In-memory stand-in for [NetDataRepository]. Use it in unit tests so a screen
 * can be exercised without https://your-server.com.
 */
class FakeNetDataRepository : NetDataRepositorySource {

    override fun requestCoopOrgan(): Flow<Resource<List<CoopOrganModel>>> = success(listOf(CoopOrganModel("mock")))

    override fun getCaptchaSmsRequest(hashMap: HashMap<String, String>): Flow<Resource<String>> = success("ok")

    override fun getCaptchaSmsRequest(orderModel: OrderModel): Flow<Resource<String>> = success("ok")

    override fun getTestRequest(): Flow<Resource<String>> = success("hello")

    override fun getOrderModelReq(): Flow<Resource<OrderModel>> = success(OrderModel(1.0, "mock-product"))

    override fun getIotTriplet(hashMap: HashMap<String, Any>): Flow<Resource<IotTripletM>> = success(
        IotTripletM(
            clientId = "mock-client",
            passwordHash = "mock-password",
            userName = "mock-device",
            remoteHost = "127.0.0.1",
            remotePort = "1883"
        )
    )

    override fun getSnHardcode(hashMap: HashMap<String, Any>): Flow<Resource<SnHardcode>> = success(
        SnHardcode(clientId = "mock-client", hardcode = "mock-hardcode", sn = "EMULATOR00000000")
    )

    override fun upgradeStatus(sn: String, ts: String, model: OtaUpgradeStatusModel): Flow<Resource<GeneModel>> =
        success(GeneModel())

    override fun getLatestPackage(hashMap: HashMap<String, Any>): Flow<Resource<OtaDataModel>> {
        val item = PackageModel(0, "1.0.0-mock", "http://127.0.0.1:8080/files/mock.zip", "d41d8cd98f00b204e9800998ecf8427e", "mock", 0L)
        return success(
            OtaDataModel(
                romVersion = "1.0.0-mock",
                mcuVersion = "1.0.0-mock",
                wholeUrl = "http://127.0.0.1:8080/files/mock.zip",
                md5 = "d41d8cd98f00b204e9800998ecf8427e",
                updateDesc = "mock package, nothing to install",
                byteSize = 0L,
                updateTime = 0L,
                packageCollectionModel = PackageCollectionModel(item, item)
            )
        )
    }

    private fun <T> success(value: T): Flow<Resource<T>> = flow { emit(Resource.Success(value)) }
}
