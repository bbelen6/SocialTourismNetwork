package com.miwfem.socialtourismnetwork.businesslogic.di

import com.miwfem.socialtourismnetwork.businesslogic.usecase.LocationsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { LocationsUseCase(get()) }
}