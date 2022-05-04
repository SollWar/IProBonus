package com.example.sollwar.iprobonusapi

class Bonus(
    private val idClient: String,
    private val idDevice: String
) {
    private val accessKey = "891cf53c-01fc-4d74-a14c-592668b7a03c"
    private var accessToken = ""
    private val iProBonusRepository: IProBonusRepository = IProBonusRepository()

    suspend fun refreshToken(): Boolean {
        val accessTokenResult = iProBonusRepository.getAccessToken(
            accessKey, idClient, idDevice
        )
        return if (accessTokenResult != null) {
            accessToken = accessTokenResult.accessToken
            true
        } else {
            false
        }
    }

    suspend fun getUserBonus(): UserBonus? {
        val bonusResult = iProBonusRepository.getBonusForToken(
            accessToken,
            accessKey
        )
        return if (bonusResult != null) {
            UserBonus(
                bonusResult.data.currentQuantity,
                bonusResult.data.dateBurning,
                bonusResult.data.forBurningQuantity,
                bonusResult.data.typeBonusName
            )
        } else return null
    }

    data class UserBonus(
        val currentQuantity: Double,
        val dateBurning: String,
        val forBurningQuantity: Double,
        val typeBonusName: String
    )

}