package com.miwfem.socialtourismnetwork.businesslogic.di

import com.miwfem.socialtourismnetwork.businesslogic.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetLocationsUseCase(get()) }
    factory { SavePostUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { SaveCategoryUseCase(get()) }
    factory { GetPostsUseCase(get()) }
}