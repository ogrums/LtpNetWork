package com.letianpai.network.template.upload.repository

import com.letianpai.network.model.GeneModel
import com.letianpai.network.model.OtaUpgradeStatusModel
import com.letianpai.network.net.Resource
import com.letianpai.network.template.repository.NetDataRepositorySource
import kotlinx.coroutines.flow.Flow

interface PhotoDataRepositorySource: NetDataRepositorySource {
    fun upgradePhoto(sn: String , model: OtaUpgradeStatusModel): Flow<Resource<GeneModel>>
}