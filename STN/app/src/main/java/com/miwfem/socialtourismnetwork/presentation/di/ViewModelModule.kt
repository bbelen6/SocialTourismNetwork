package com.miwfem.socialtourismnetwork.presentation.di

import com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel.AddPostViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.auth.viewmodel.AuthViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel() }
    viewModel { AddPostViewModel(get(), get(), get()) }
    viewModel { HomeViewModel(get()) }
}