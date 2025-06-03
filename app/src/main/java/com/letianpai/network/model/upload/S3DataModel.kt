package com.letianpai.network.model.upload
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json



@JsonClass(generateAdapter = true)
data class S3DataModel(
    @Json(name = "access_key")
    val accessKey: String,
    @Json(name = "bucket")
    val bucket: String?,
    @Json(name = "download_url")
    val downloadUrl: String,
    @Json(name = "expire_time")
    val expireTime: Int?,
    @Json(name = "object_key")
    val objectKey: String?,
    @Json(name = "object_pre_key")
    val objectPreKey: String,
    @Json(name = "secret_key")
    val secretKey: String,
    @Json(name = "session_token")
    val sessionToken: String
)