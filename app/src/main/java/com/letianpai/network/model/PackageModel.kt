package com.letianpai.network.model

import com.squareup.moshi.Json

data class PackageModel(
    @Json(name = "is_update")
    val isUpdate: Int,
    @Json(name = "version")
    val version: String,
    @Json(name = "rom_package_url")
    val romPackageUrl: String,
    @Json(name = "md5")
    val md5: String,
    @Json(name = "upgrade_desc")
    val upgradeDesc: String,
    @Json(name = "update_time")
    val updateTime: Long,
)