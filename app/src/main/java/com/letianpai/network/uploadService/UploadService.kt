package com.letianpai.network.uploadService

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.elvishew.xlog.XLog

import com.letianpai.network.common.SystemUtil
import com.letianpai.network.model.upload.PhotoModel
import com.letianpai.network.model.upload.S3DataModel
import com.letianpai.network.model.upload.UploadTokenModel
import com.letianpai.network.template.upload.repository.PhotoDataRepository
import com.letianpai.network.model.upload.SnxCredentialsProvider
import com.qiniu.android.storage.Configuration
import com.qiniu.android.storage.UploadManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.io.File

class UploadService : Service() {

    private var filePath = ""
    private var modelPath:String? = ""

    companion object {
        const val TAG = "UploadService"
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            var path = intent.getStringExtra("path")
            modelPath = intent.getStringExtra("modelPath")
            if (!path.isNullOrEmpty()) {
                filePath = path
                upload()
            }
            //默认是录音
            if (modelPath.isNullOrEmpty()){
                modelPath = "record"
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun upload() {
        GlobalScope.launch(Dispatchers.IO) {
            val pro: String = SystemUtil.get(SystemUtil.REGION_LANGUAGE, "zh")
            if (pro != "zh") {
                getS3Session()
            } else {
                getUploadToken()
            }
        }
    }

    private suspend fun getUploadToken() {
        XLog.i("TakePhotoService"+ "--getUploadToken:modelPath:${modelPath} ")
        val repo = PhotoDataRepository(this)
        repo.getUploadToken(modelPath!!).collect {
            it.error?.let {
                XLog.i("TakePhotoService"+ "--test upload params: ${it.code} ${it.description}")
            }
            it.data?.let {
                XLog.i("TakePhotoService"+ "--test3 upload params: ${it}")
                uploadFile(it)
            }
        }
    }

    private suspend fun uploadFile(uploadTokenModel: UploadTokenModel) {
        var data = filePath
        XLog.i("TakePhotoService--uploadFile: $data")
        var config = Configuration.Builder().build()
        var uploadManager = UploadManager(config)
        uploadManager.put(
            data, uploadTokenModel.fileKey, uploadTokenModel.uploadToken, { key, info, response ->
                if (info?.isOK == true) {
                    XLog.i("qiniu"+ "--Upload Success")
                    GlobalScope.async {
                        bindAccount(uploadTokenModel.downloadUrl)

                    }
                } else {
                    XLog.i("qiniu"+ "--Upload Fail");
                    //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                }
            }, null
        )
    }

    private suspend fun getS3Session() {
        val repo = PhotoDataRepository(this)
        repo.getS3UploadToken(modelPath!!).collect {
            it.error?.let {
                XLog.i(
                    "TakePhotoService"+
                    "--getS3UploadToken error test upload params: ${it.code} ${it.description}"
                )
            }
            it.data?.let {
                XLog.i("TakePhotoService"+ "--getS3UploadToken test3 upload params: ${it}")
                uploadFileS3(this@UploadService, filePath, it)
            }
        }
    }

    suspend fun uploadFileS3(context: Context, name: String, s3DataModel: S3DataModel) {
        var file = File(name)

        var transferUtility =
            TransferUtility.builder()
                .context(context)
                .defaultBucket(s3DataModel.bucket)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .s3Client(
                    AmazonS3Client(
                        SnxCredentialsProvider(
                            s3DataModel.accessKey,
                            s3DataModel.secretKey,
                            s3DataModel.sessionToken
                        ),
                        Region.getRegion(Regions.US_EAST_1)
                    )
                )
                .build()
        val observer =
            transferUtility.upload(
                "${s3DataModel.objectKey}",
                file,
                CannedAccessControlList.AuthenticatedRead
            )
        observer.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                XLog.i("$TAG--onStateChanged: $id   state:$state")
                if (state == TransferState.COMPLETED) {
                    runBlocking {
                        bindAccount(s3DataModel.downloadUrl)
                    }
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                XLog.i(
                    TAG+
                    "---onProgressChanged: id:$id  bytesCurrent:$bytesCurrent  bytesTotal:$bytesTotal"
                )
            }

            override fun onError(id: Int, ex: java.lang.Exception?) {
                XLog.i(TAG+ "---onError: id:$id  ")
                ex?.printStackTrace()
            }
        })
    }

    private suspend fun bindAccount(downloadUrl: String) {
        val repo = PhotoDataRepository(this)
        var model = PhotoModel(downloadUrl)
        repo.upgradePhoto(model = model).collect {
            XLog.i("TakePhotoService"+ "---test3: ")
            it.error?.let {
                XLog.i("TakePhotoService"+ "---test3: ${it.code} ${it.description}")
            }
            it.data?.let {
                XLog.i("TakePhotoService"+ "---test3: ${it}")
                XLog.i("$TAG---bindAccount: 上传成功")
                GlobalScope.launch {
//                    delay(5000)
                    stopSelf()
                }
            }
        }
        File(filePath).deleteRecursively()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}