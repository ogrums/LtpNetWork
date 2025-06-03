package com.letianpai.network.model.upload

import com.amazonaws.auth.AWSSessionCredentials

class SnxCredentialsProvider constructor(
    val accessKey: String,
    val secretKey: String,
    val session: String
) : AWSSessionCredentials {
    override fun getAWSAccessKeyId(): String = accessKey

    override fun getAWSSecretKey(): String = secretKey

    override fun getSessionToken(): String = session

}