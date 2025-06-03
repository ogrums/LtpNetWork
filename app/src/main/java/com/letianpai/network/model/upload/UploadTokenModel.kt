package com.letianpai.network.model.upload

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class UploadTokenModel(
    @Json(name = "download_url")
    var downloadUrl: String,
    @Json(name = "file_key")
    var fileKey: String,
    @Json(name = "upload_domain")
    var uploadDomain: String,
    @Json(name = "upload_token")
    var uploadToken: String
)