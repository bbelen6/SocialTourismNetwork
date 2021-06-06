package com.miwfem.socialtourismnetwork.businesslogic.di

import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetLocationsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetLocationsUseCase(get()) }
}