package com.example.taskassignmentpacedarshan.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.taskassignmentpacedarshan.R
import com.example.taskassignmentpacedarshan.common.base.BaseFragment
import com.example.taskassignmentpacedarshan.databinding.FragmentProtradeBinding
import com.example.taskassignmentpacedarshan.ui.add_address.view.AddAddressActivity
import com.example.taskassignmentpacedarshan.ui.main.viewmodel.MainFragmentViewModel
import org.koin.android.ext.android.inject


class ProTradeFragment :BaseFragment(){
    private val mainFragmentViewModel : MainFragmentViewModel by inject()
    private var binding : FragmentProtradeBinding?=null

    override fun getVm(): ViewModel {
     return mainFragmentViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        if (binding != null){
            return binding?.root
        }
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_protrade,container,false)
        binding?.mainFragmentViewModel = mainFragmentViewModel
        binding?.lifecycleOwner = viewLifecycleOwner


        return binding?.root
    }
}