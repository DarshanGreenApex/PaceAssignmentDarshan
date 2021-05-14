package com.example.taskassignmentpacedarshan.data.modules

import com.example.taskassignmentpacedarshan.ui.add_address.viewmodel.AddAddressViewModel
import com.example.taskassignmentpacedarshan.ui.main.viewmodel.MainFragmentViewModel
import com.example.taskassignmentpacedarshan.ui.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(),get()) }
    viewModel { MainFragmentViewModel(get(),get()) }
    viewModel { AddAddressViewModel(get(),get()) }
}