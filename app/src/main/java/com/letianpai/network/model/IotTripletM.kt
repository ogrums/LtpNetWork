package com.letianpai.network.model

import com.squareup.moshi.Json

data class IotTripletM(
    @Json(name = "client_id")
    val clientId: String,
    @Json(name = "password_hash")
    val passwordHash: String,
    @Json(name = "user_name")
    val userName: String,
    @Json(name = "remote_host")
    val remoteHost: String,
    @Json(name = "remote_port")
    val remotePort: String,
)