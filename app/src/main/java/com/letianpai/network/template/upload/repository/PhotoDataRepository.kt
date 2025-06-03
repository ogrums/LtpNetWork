package com.letianpai.network.template.upload.repository

import android.content.Context
import com.letianpai.network.model.upload.PhotoModel
import com.letianpai.network.model.upload.PhotoResponseModel
import com.letianpai.network.model.upload.S3DataModel
import com.letianpai.network.model.upload.UploadTokenModel
import com.letianpai.network.net.Resource
import com.letianpai.network.template.repository.NetDataRepository
import com.letianpai.network.template.upload.datasource.PhotoRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class PhotoDataRepository(context: Context) : NetDataRepository(context) {
    private var remoteData: PhotoRemoteData
    private var ioDispatcher: CoroutineContext

    init {
        remoteData = PhotoRemoteData(context)
        ioDispatcher = Dispatchers.IO
    }

    fun upgradePhoto(model: PhotoModel): Flow<Resource<PhotoResponseModel>> {
        return flow {
            emit(remoteData.upgradePhoto(model))
        }.flowOn(ioDispatcher)
    }

    fun getUploadToken(modelPath: String): Flow<Resource<UploadTokenModel>> {
        return flow {
            emit(remoteData.getUploadToken(modelPath))
        }.flowOn(ioDispatcher)
    }
    fun getS3UploadToken(modelPath: String): Flow<Resource<S3DataModel>> {
        return flow {
            emit(remoteData.getS3UploadToken(modelPath))
        }.flowOn(ioDispatcher)
    }

}