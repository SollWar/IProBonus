package com.example.sollwar.iprobonusapi.model.modelGetBonusForToken

data class ResultOperation(
    val codeResult: Int,
    val duration: Double,
    val idLog: String,
    val message: String,
    val messageDev: Any,
    val status: Int
)