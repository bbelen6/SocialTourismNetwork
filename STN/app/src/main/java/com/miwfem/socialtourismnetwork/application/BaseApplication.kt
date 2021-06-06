package com.miwfem.socialtourismnetwork.application

import android.app.Application
import com.miwfem.socialtourismnetwork.businesslogic.di.useCaseModule
import com.miwfem.socialtourismnetwork.data.di.cmApiModule
import com.miwfem.socialtourismnetwork.data.di.firebaseModule
import com.miwfem.socialtourismnetwork.data.di.retrofitModule
import com.miwfem.socialtourismnetwork.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(viewModelModule, retrofitModule, cmApiModule, useCaseModule, firebaseModule)
        }
    }
}