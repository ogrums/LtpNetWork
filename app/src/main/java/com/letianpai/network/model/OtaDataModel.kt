package com.letianpai.network.model

import com.squareup.moshi.Json

data class OtaDataModel(
    @Json(name = "rom_version")
    val romVersion: String,

    @Json(name = "mcu_version")
    val mcuVersion: String,

    @Json(name = "whole_package_url")
    val wholeUrl: String,

    @Json(name = "md5")
    val md5: String,

    @Json(name = "upgrade_desc")
    val updateDesc: String,

    @Json(name = "byte_size")
    val byteSize: Long,

    @Json(name = "update_time")
    val updateTime: Long,

    @Json(name = "package_collection")
    val packageCollectionModel: PackageCollectionModel,

)