package com.miwfem.socialtourismnetwork.businesslogic.di

import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetCategoriesUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetLocationsUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.SaveCategoryUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.SavePostUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetLocationsUseCase(get()) }
    factory { SavePostUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { SaveCategoryUseCase(get()) }
}