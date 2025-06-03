package com.letianpai.network.model

import com.squareup.moshi.Json

data class SnHardcode(
    @Json(name = "client_id")
    val clientId: String,
    @Json(name = "hard_code")
    val hardcode: String,
    @Json(name = "sn")
    val sn: String,
)