package com.miwfem.socialtourismnetwork.data.di

import com.miwfem.socialtourismnetwork.businesslogic.repository.ICMRepository
import com.miwfem.socialtourismnetwork.data.datasource.api.ComunidadMadridService
import com.miwfem.socialtourismnetwork.data.datasource.source.CMDataSource
import com.miwfem.socialtourismnetwork.data.repository.CMRepositoryImpl
import com.miwfem.socialtourismnetwork.utils.COMUNIDAD_MADRID_BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single { provideRetrofit(COMUNIDAD_MADRID_BASE_URL) }
}

private fun provideRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val cmApiModule = module {
    factory { provideCMService(get()) }
    factory { CMDataSource(get()) }
    single { CMRepositoryImpl(get(), get()) as ICMRepository }
}

private fun provideCMService(retrofit: Retrofit): ComunidadMadridService =
    retrofit.create(ComunidadMadridService::class.java)