package com.example.sollwar.iprobonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sollwar.iprobonusapi.Bonus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bonus = Bonus(
            "2c44d8c2-c89a-472e-aab3-9a8a29142315",
            "7db72635-fd0a-46b9-81asdasd3b-1627e3aa02ea"
        )

        CoroutineScope(Dispatchers.Main).launch {
            val testToken = bonus.refreshToken()
            val testUserBonus = bonus.getUserBonus()
            Log.d("TestBonus", testToken.toString())
            Log.d("TestBonus", testUserBonus.toString())
        }
    }
}