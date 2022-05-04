package com.example.sollwar.iprobonusapi.retrofit

import com.example.sollwar.iprobonusapi.model.modelAccesToken.AccessTokenBody
import com.example.sollwar.iprobonusapi.model.modelAccesToken.AccessTokenResult
import com.example.sollwar.iprobonusapi.model.modelGetBonusForToken.BonusResult
import retrofit2.Response
import retrofit2.http.*

interface IProBonusApi {
    @POST("/api/v3/clients/accesstoken")
    suspend fun getAccessToken(
        @Header("AccessKey") accessKey: String,
        @Body accessTokenBody: AccessTokenBody
    ): Response<AccessTokenResult>

    @GET("/api/v3/ibonus/generalinfo/{token}")
    suspend fun getBonusForToken(
        @Path("token") accessToken: String,
        @Header("AccessKey") accessKey: String
    ): Response<BonusResult>
}