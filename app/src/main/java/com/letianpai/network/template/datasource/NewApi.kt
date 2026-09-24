package com.letianpai.network.template.datasource

import com.letianpai.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface NewApi {

    // Fetch the long-connection IoT triplet
    @GET("/robot_api/v1/bind/getIotTriplet")
    suspend fun getIotTriplet( @QueryMap hashMap: HashMap<String, Any>): Response<BaseResultBean<IotTripletM>>


    // Fetch SN and hardcode
    @GET("/robot_api/v1/bind/getSnByMac")
    suspend fun getSnHardcode( @QueryMap hashMap: HashMap<String, Any>): Response<BaseResultBean<SnHardcode>>


    // Report the download progress
    @POST("/robot_api/v1/device/upgrade/status")
    suspend fun upgradeStatus(@Query("sn") sn: String, @Query("ts") ts: String,  @Body model: OtaUpgradeStatusModel): Response<BaseResultBean<GeneModel>>

    // Fetch the latest OTA package
    @GET("/robot_api/v1/ota/getLatestPackage")
    suspend fun getLatestPackage(@QueryMap hashMap: HashMap<String, Any>): Response<BaseResultBean<OtaDataModel>>


    // -------------------------------------------------------------
    // Submit a test order
    @POST("/index/addorder")
    suspend fun getCaptchaSmsRequest(
        @Body order: OrderModel
    ): Response<BaseResultBean<String>>

    @POST("addons/shop/checkout/submit")
    suspend fun getCaptchaSmsRequestMap(
        @QueryMap hashMap: HashMap<String, String>
    ): Response<BaseResultBean<String>>

    // Test API
    @GET("index/hello")
    suspend fun getTestRequest(): Response<BaseResultBean<String>>

    // Test API
    @GET("index/getorder")
    suspend fun getOrderModelReq(): Response<BaseResultBean<OrderModel>>

}