package com.letianpai.network.template.datasource

import com.letianpai.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface NewApi {

    //获取长连三元组
    @GET("/robot_api/v1/bind/getIotTriplet")
    suspend fun getIotTriplet( @QueryMap hashMap: HashMap<String, Any>): Response<BaseResultBean<IotTripletM>>


    //获取SN 和 hardcode
    @GET("/robot_api/v1/bind/getSnByMac")
    suspend fun getSnHardcode( @QueryMap hashMap: HashMap<String, Any>): Response<BaseResultBean<SnHardcode>>


    //更新下载进度
    @POST("/robot_api/v1/device/upgrade/status")
    suspend fun upgradeStatus(@Query("sn") sn: String, @Query("ts") ts: String,  @Body model: OtaUpgradeStatusModel): Response<BaseResultBean<GeneModel>>

    //获取OTA版本信息
    @GET("/robot_api/v1/ota/getLatestPackage")
    suspend fun getLatestPackage(@QueryMap hashMap: HashMap<String, Any>): Response<BaseResultBean<OtaDataModel>>


    // -------------------------------------------------------------
    //提交订单测试
    @POST("/index/addorder")
    suspend fun getCaptchaSmsRequest(
        @Body order: OrderModel
    ): Response<BaseResultBean<String>>

    @POST("addons/shop/checkout/submit")
    suspend fun getCaptchaSmsRequestMap(
        @QueryMap hashMap: HashMap<String, String>
    ): Response<BaseResultBean<String>>

    //测试接口
    @GET("index/hello")
    suspend fun getTestRequest(): Response<BaseResultBean<String>>

    //测试接口
    @GET("index/getorder")
    suspend fun getOrderModelReq(): Response<BaseResultBean<OrderModel>>

}