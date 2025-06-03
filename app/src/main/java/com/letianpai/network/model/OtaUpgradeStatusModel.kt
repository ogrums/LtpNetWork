package com.letianpai.network.model

import com.squareup.moshi.Json

data class OtaUpgradeStatusModel(
    @Json(name = "system_upgrade_version")
    val version: String,
    @Json(name = "upgrade_status")
    val status: Int,
)