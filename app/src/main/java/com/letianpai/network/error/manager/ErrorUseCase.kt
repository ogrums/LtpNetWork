package com.letianpai.network.error.manager

import com.letianpai.network.error.HSError


interface ErrorUseCase {
    fun getError(errorCode: Int): HSError
}
