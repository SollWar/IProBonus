package com.example.sollwar.iprobonusapi

class UserBonus(
    private val idClient: String,
    private val idDevice: String
) {
    private val accessKey = "891cf53c-01fc-4d74-a14c-592668b7a03c"
    private var accessToken = "null"
    var accessTokenAvailable = false
    private var iProBonusRepository = IProBonusRepository.getInstance()

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

    suspend fun getUserBonus(): UserBonusData? {
        iProBonusRepository = IProBonusRepository()
        try {
            val bonusResult = iProBonusRepository.getBonusForToken(
                accessToken,
                accessKey
            )
            return if (bonusResult != null) {
                UserBonusData(
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

    data class UserBonusData(
        val currentQuantity: Double,
        val dateBurning: String,
        val forBurningQuantity: Double,
        val typeBonusName: String
    )

    companion object {
        private var userBonus: UserBonus? = null
        fun getInstance(idClient: String, idDevice: String): UserBonus {
            return if (userBonus == null) {
                userBonus = UserBonus(idClient, idDevice)
                userBonus!!
            } else userBonus!!
        }
    }

}