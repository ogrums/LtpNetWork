package com.letianpai.network.error.manager


import com.letianpai.network.error.HSError
import com.letianpai.network.error.mapper.ErrorMapper

/**
 * Created by jianbin on 2023.01.19
 */

class ErrorManager constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): HSError {
        return HSError(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
