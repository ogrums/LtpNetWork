package com.letianpai.network.model

import com.squareup.moshi.Json


data class BaseResultBean<T>(
    @Json(name = "code")
    var code: Int,
    @Json(name = "msg")
    var msg: String,
    @Json(name = "data")
    var data: T? = null
)