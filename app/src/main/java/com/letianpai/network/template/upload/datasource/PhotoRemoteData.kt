package com.letianpai.network.template.upload.datasource

import android.content.Context
import com.letianpai.network.common.AppUtils
import com.letianpai.network.model.upload.PhotoModel
import com.letianpai.network.model.upload.PhotoResponseModel
import com.letianpai.network.model.upload.S3DataModel
import com.letianpai.network.model.upload.UploadTokenModel
import com.letianpai.network.net.Resource
import com.letianpai.network.net.ServiceGenerator
import com.letianpai.network.template.datasource.RemoteData

class PhotoRemoteData(context: Context) : RemoteData(context) {
    private var newApiService: Api = ServiceGenerator().createService(Api::class.java)

    suspend fun upgradePhoto(model: PhotoModel): Resource<PhotoResponseModel> {
        val hashMap = hashMapOf(
            Pair("sn", AppUtils.getSerialNo()), Pair("ts", "${System.currentTimeMillis() / 1000}")
        )
        val response = processCall2PramasMapAndBody(newApiService::upgradePhoto, hashMap, model)
        return if (response is PhotoResponseModel) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }

    suspend fun getUploadToken(modelPath: String): Resource<UploadTokenModel> {
        val hashMap = hashMapOf(
            Pair("sn", AppUtils.getSerialNo()),
            Pair("ts", "${System.currentTimeMillis() / 1000}"),
            Pair("model_path", modelPath),
        )
        val response = processCallPramasMap(newApiService::getUploadToken, hashMap)
        return if (response is UploadTokenModel) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }

    suspend fun getS3UploadToken(modelPath: String): Resource<S3DataModel> {
        val hashMap = hashMapOf(
            Pair("model_path", modelPath),
            Pair("sn", AppUtils.getSerialNo()),
            Pair("ts", "${System.currentTimeMillis() / 1000}")
        )
        val response = processCallPramasMap(newApiService::getS3SessionToken, hashMap)
        return if (response is S3DataModel) {
            Resource.Success(data = response)
        } else {
            responseError(response)
        }
    }
}