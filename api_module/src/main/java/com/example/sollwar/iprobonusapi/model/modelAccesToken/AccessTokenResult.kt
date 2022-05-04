package com.example.sollwar.iprobonusapi.model.modelAccesToken

internal data class AccessTokenResult(
    val accessToken: String,
    val result: Result
)

internal data class Result(
    val codeResult: Int,
    val duration: Double,
    val idLog: String,
    val message: String,
    val messageDev: Any,
    val status: Int
)