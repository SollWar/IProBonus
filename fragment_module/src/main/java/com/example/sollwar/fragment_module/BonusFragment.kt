package com.example.sollwar.fragment_module

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sollwar.fragment_module.databinding.FragmentBonusBinding
import java.text.SimpleDateFormat

const val ID_CLIENT = "ID_CLIENT"
const val ID_DEVICE = "ID_DEVICE"

class BonusFragment : Fragment() {

    private var _binding: FragmentBonusBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BonusViewModel>()
    private var idClient = ""
    private var idDevice = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBonusBinding.inflate(inflater, container, false)
        idClient = arguments?.getString(ID_CLIENT) as String
        idDevice = arguments?.getString(ID_DEVICE) as String

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onStart() {
        super.onStart()
        viewModel.getUserBonus(idClient, idDevice)
        viewModel.userBonusLiveData.observe(
            viewLifecycleOwner
        ) { userBonus ->
            userBonus.let {
                binding.bonusView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(userBonus.dateBurning)
                        ?.let { it1 ->
                            SimpleDateFormat("dd.MM").format(
                                it1
                            )
                        }
                binding.bonusCount.text =
                    getString(R.string.bonuses, userBonus.currentQuantity.toInt().toString())
                binding.bonusFire.text =
                    getString(R.string.bonuses, userBonus.forBurningQuantity.toInt().toString())
                binding.dataFire.text = getString(R.string.date_burn, dateFormat)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(
            idClient: String,
            idDevice: String
        ) = BonusFragment().apply {
            arguments = Bundle().apply {
                putString(ID_CLIENT, idClient)
                putString(ID_DEVICE, idDevice)
            }
        }
    }
}