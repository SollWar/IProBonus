package com.example.sollwar.iprobonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sollwar.fragment_module.BonusFragment
import com.example.sollwar.iprobonus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idClient = "2c44d8c2-c89a-472e-aab3-9a8a29142315"
        val idDevice = "7db72635-fd0a-46b9-81asdasd3b-1627e3aa02ea"
        val scale = 1.0f

        binding.cardView.scaleX = scale
        binding.cardView.scaleY = scale

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    binding.fragmentContainer.id, BonusFragment.newInstance(
                        idClient,
                        idDevice
                    )
                )
                .commit()
        }
    }
}