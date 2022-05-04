package com.example.sollwar.iprobonusapi.model.modelGetBonusForToken

internal data class BonusResult(
    val data: Data,
    val resultOperation: ResultOperation
)

internal data class Data(
    val currentQuantity: Double,
    val dateBurning: String,
    val forBurningQuantity: Double,
    val typeBonusName: String
)

internal data class ResultOperation(
    val codeResult: Int,
    val duration: Double,
    val idLog: String,
    val message: String,
    val messageDev: Any,
    val status: Int
)