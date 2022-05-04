package com.example.sollwar.iprobonusapi.model.modelAccesToken

data class Result(
    val codeResult: Int,
    val duration: Double,
    val idLog: String,
    val message: String,
    val messageDev: Any,
    val status: Int
)