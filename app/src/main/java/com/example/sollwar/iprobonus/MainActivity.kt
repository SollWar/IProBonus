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

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainer.id, BonusFragment.newInstance())
                .commit()
        }
    }
}