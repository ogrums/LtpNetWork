package com.letianpai.network.template.upload.datasource

import com.letianpai.network.model.BaseResultBean
import com.letianpai.network.model.upload.PhotoModel
import com.letianpai.network.model.upload.PhotoResponseModel
import com.letianpai.network.model.upload.S3DataModel
import com.letianpai.network.model.upload.UploadTokenModel
import com.letianpai.network.template.datasource.NewApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface Api : NewApi {
    @POST("/robot_api/v1/device/addRecord")
    suspend fun upgradePhoto(
        @QueryMap map: HashMap<String, String>,
        @Body model: PhotoModel
    ): Response<BaseResultBean<PhotoResponseModel>>

    @GET("/robot_api/v1/cloudFile/getToken")
    suspend fun getUploadToken(
        @QueryMap map: HashMap<String, String>,
    ): Response<BaseResultBean<UploadTokenModel>>

    @GET("/robot_api/v1/cloudFile/getSessionToken")
    suspend fun getS3SessionToken(
        @QueryMap map: HashMap<String, String>,
    ): Response<BaseResultBean<S3DataModel>>

}