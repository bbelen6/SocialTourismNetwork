package com.miwfem.socialtourismnetwork.presentation.di

import com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel.AddPostViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.auth.viewmodel.AuthViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel.HomeViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.info.viewmodel.InfoViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.messages.viewmodel.MessageViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { AddPostViewModel(get(), get(), get()) }
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
    viewModel { MessageViewModel(get(), get()) }
    viewModel { InfoViewModel(get()) }
}