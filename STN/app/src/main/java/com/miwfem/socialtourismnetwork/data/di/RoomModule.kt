package com.miwfem.socialtourismnetwork.data.di

import android.content.Context
import androidx.room.Room
import com.miwfem.socialtourismnetwork.data.datasource.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val roomModule = module {
    single { provideRoom(androidApplication()) }
    single { get<AppDatabase>().categoryDao() }
}

private fun provideRoom(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

