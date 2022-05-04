package com.example.sollwar.iprobonusapi

class Bonus(
    private val idClient: String,
    private val idDevice: String
) {
    private val accessKey = "891cf53c-01fc-4d74-a14c-592668b7a03c"
    private var accessToken = "null"
    var accessTokenAvailable = false
    private var iProBonusRepository: IProBonusRepository = IProBonusRepository()

    suspend fun refreshToken(): Boolean {
        iProBonusRepository = IProBonusRepository()
        return try {
            val accessTokenResult = iProBonusRepository.getAccessToken(
                accessKey, idClient, idDevice
            )
            accessToken = accessTokenResult!!.accessToken
            accessTokenAvailable = true
            true
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getUserBonus(): UserBonus? {
        iProBonusRepository = IProBonusRepository()
        try {
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
        } catch (e: Exception) {
            throw e
        }
    }

    data class UserBonus(
        val currentQuantity: Double,
        val dateBurning: String,
        val forBurningQuantity: Double,
        val typeBonusName: String
    )

    companion object {
        private var bonus: Bonus? = null
        fun getInstance(idClient: String, idDevice: String): Bonus {
            return if (bonus == null) {
                bonus = Bonus(idClient, idDevice)
                bonus!!
            } else bonus!!
        }
    }

}