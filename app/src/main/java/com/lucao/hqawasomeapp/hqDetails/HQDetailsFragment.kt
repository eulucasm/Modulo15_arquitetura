package com.lucao.hqawasomeapp.hqDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.lucao.hqawasomeapp.HQViewModel
import com.lucao.hqawasomeapp.R
import com.lucao.hqawasomeapp.databinding.FragmentHQDetailsBinding

class HQDetailsFragment : Fragment() {

    private val viewModel by navGraphViewModels<HQViewModel>(R.id.hq_graph) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentHQDetailsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_h_q_details,
                container,
                false
            )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

}