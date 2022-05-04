package com.example.sollwar.fragment_module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.sollwar.fragment_module.databinding.FragmentBonusBinding

class BonusFragment : Fragment() {

    private var _binding: FragmentBonusBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BonusViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBonusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserBonus(
            "2c44d8c2-c89a-472e-aab3-9a8a29142315",
            "7db72635-fd0a-46b9-81asdasd3b-1627e3aa02ea"
        )
        viewModel.userBonusLiveData.observe(
            viewLifecycleOwner,
            Observer { userBonus ->
                userBonus.let {
                    binding.bonusCount.text = userBonus.currentQuantity.toString()
                    binding.bonusFire.text = userBonus.forBurningQuantity.toString()
                    binding.dataFire.text = userBonus.dateBurning
                    binding.bonusName.text = userBonus.typeBonusName
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = BonusFragment()
    }
}