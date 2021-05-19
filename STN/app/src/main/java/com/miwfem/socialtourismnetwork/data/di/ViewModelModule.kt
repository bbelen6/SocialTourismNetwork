package com.miwfem.socialtourismnetwork.data.di

import com.miwfem.socialtourismnetwork.data.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel() }
}