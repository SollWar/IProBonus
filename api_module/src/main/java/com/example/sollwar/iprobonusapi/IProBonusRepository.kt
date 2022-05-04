package com.example.sollwar.iprobonusapi

import com.example.sollwar.iprobonusapi.model.modelAccesToken.AccessTokenBody
import com.example.sollwar.iprobonusapi.model.modelAccesToken.AccessTokenResult
import com.example.sollwar.iprobonusapi.model.modelGetBonusForToken.BonusResult
import com.example.sollwar.iprobonusapi.retrofit.IProBonusApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val INVALID_TOKEN_EXCEPTION = Exception("Invalid AccessToken")
val SOMETHING_WENT_WRONG_EXCEPTION = Exception("Something went wrong")
val NOT_FOUND_CLIENT_WITH_ID_EXCEPTION = Exception("Not found client with ID")

class IProBonusRepository {
    private var iProBonusApi: IProBonusApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mp1.iprobonus.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        iProBonusApi = retrofit.create(IProBonusApi::class.java)
    }

    suspend fun getAccessToken(
        accessKey: String,
        idClient: String,
        idDevice: String
    ): AccessTokenResult? {
        try {
            val request = iProBonusApi.getAccessToken(accessKey, AccessTokenBody(
                idClient = idClient,
                paramValue = idDevice
            ))
            when (request.body()!!.result.status) {
                0 -> return request.body()
                1 -> throw NOT_FOUND_CLIENT_WITH_ID_EXCEPTION
                else -> throw SOMETHING_WENT_WRONG_EXCEPTION
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getBonusForToken(
        accessToken: String,
        accessKey: String
    ): BonusResult? {
        try {
            val request = iProBonusApi.getBonusForToken(accessToken, accessKey)
            when (request.body()!!.resultOperation.status) {
                0 -> return request.body()
                1 -> throw INVALID_TOKEN_EXCEPTION
                else -> throw SOMETHING_WENT_WRONG_EXCEPTION
            }
        } catch (e: Exception) {
            throw e
        }
    }
}