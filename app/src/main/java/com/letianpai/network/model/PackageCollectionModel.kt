package com.letianpai.network.model

import com.squareup.moshi.Json

data class PackageCollectionModel(
    @Json(name = "rom_package")
    val romPackage: PackageModel,
    @Json(name = "mcu_a_package")
    val mcuPackage: PackageModel)