package com.example.sollwar.iprobonusapi

import com.example.sollwar.iprobonusapi.model.modelAccesToken.AccessTokenBody
import com.example.sollwar.iprobonusapi.model.modelAccesToken.AccessTokenResult
import com.example.sollwar.iprobonusapi.model.modelGetBonusForToken.BonusResult
import com.example.sollwar.iprobonusapi.retrofit.IProBonusApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IProBonusRepository {
    private val iProBonusApi: IProBonusApi

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
        return iProBonusApi.getAccessToken(accessKey, AccessTokenBody(
            idClient = idClient,
            paramValue = idDevice
        )).body()
    }

    suspend fun getBonusForToken(
        accessToken: String,
        accessKey: String
    ): BonusResult? {
        return iProBonusApi.getBonusForToken(accessToken, accessKey).body()
    }
}