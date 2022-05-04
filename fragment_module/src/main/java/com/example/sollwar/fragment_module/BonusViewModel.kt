package com.example.sollwar.fragment_module

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sollwar.iprobonusapi.UserBonus
import com.example.sollwar.iprobonusapi.INVALID_TOKEN_EXCEPTION
import kotlinx.coroutines.*

internal class BonusViewModel : ViewModel() {

    private lateinit var userBonus: UserBonus
    val userBonusLiveData = MutableLiveData<UserBonus.UserBonusData>()

    fun getUserBonus(
        idClient: String,
        idDevice: String
    ) {

        val refreshToken: Deferred<Boolean?> = viewModelScope.async(
            Dispatchers.IO,
            CoroutineStart.LAZY
        ) {
            return@async userBonus.refreshToken()
        }

        val requestUserUserBonusData: Deferred<UserBonus.UserBonusData?> = viewModelScope.async(
            Dispatchers.IO,
            CoroutineStart.LAZY
        ) {
            return@async userBonus.getUserBonus()
        }

        viewModelScope.launch {
            userBonus = UserBonus.getInstance(idClient, idDevice)
            // Если токен уже получен
            if (userBonus.accessTokenAvailable) {
                try {
                    requestUserUserBonusData.start()
                    userBonusLiveData.value = requestUserUserBonusData.await()
                } catch (e: Exception) {
                    // Если токен устарел
                    if (e == INVALID_TOKEN_EXCEPTION) {
                        userBonus.accessTokenAvailable = false
                        getUserBonus(idClient, idDevice)
                    } else {
                        Log.d("ViewModelException", e.toString())
                        Log.d("ViewModelException", "Повторная попытка через 2 с.")
                        delay(2000)
                        getUserBonus(idClient, idDevice)
                    }
                }
            } else {
                try {
                    refreshToken.start()
                    refreshToken.await()
                    requestUserUserBonusData.start()
                    userBonusLiveData.value = requestUserUserBonusData.await()
                } catch (e: Exception) {
                    Log.d("ViewModelException", e.toString())
                    Log.d("ViewModelException", "Повторная попытка через 2 с.")
                    delay(2000)
                    getUserBonus(idClient, idDevice)
                }
            }
        }
    }


}