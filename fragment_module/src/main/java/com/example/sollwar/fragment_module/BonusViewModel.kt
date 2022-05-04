package com.example.sollwar.fragment_module

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sollwar.iprobonusapi.Bonus
import com.example.sollwar.iprobonusapi.INVALID_TOKEN_EXCEPTION
import kotlinx.coroutines.*

class BonusViewModel : ViewModel() {

    private lateinit var bonus: Bonus
    val userBonusLiveData = MutableLiveData<Bonus.UserBonus>()

    fun getUserBonus(
        idClient: String,
        idDevice: String
    ) {

        val refreshToken: Deferred<Boolean?> = viewModelScope.async(
            Dispatchers.IO,
            CoroutineStart.LAZY
        ) {
            return@async bonus.refreshToken()
        }
        val requestUserBonus: Deferred<Bonus.UserBonus?> = viewModelScope.async(
            Dispatchers.IO,
            CoroutineStart.LAZY
        ) {
            return@async bonus.getUserBonus()
        }

        viewModelScope.launch {
            bonus = Bonus.getInstance(idClient, idDevice)
            if (bonus.accessTokenAvailable) {
                try {
                    requestUserBonus.start()
                    userBonusLiveData.value = requestUserBonus.await()
                } catch (e: Exception) {
                    if (e == INVALID_TOKEN_EXCEPTION) {
                        bonus.accessTokenAvailable = false
                        getUserBonus(idClient, idDevice)
                    } else {
                        Log.d("ViewModelException", e.toString())
                        Log.d("ViewModelException", "Повторная попытка через 5 с.")
                        delay(5000)
                        getUserBonus(idClient, idDevice)
                    }
                }
            } else {
                try {
                    refreshToken.start()
                    refreshToken.await()
                    requestUserBonus.start()
                    userBonusLiveData.value = requestUserBonus.await()
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