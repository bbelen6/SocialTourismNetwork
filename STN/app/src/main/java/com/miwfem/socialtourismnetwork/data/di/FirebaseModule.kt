package com.miwfem.socialtourismnetwork.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.data.datasource.source.FirebaseDataSource
import com.miwfem.socialtourismnetwork.data.repository.FirebaseRepositoryImpl
import org.koin.dsl.module

val firebaseModule = module {
    single { provideFirebase() }
    factory { FirebaseDataSource(get()) }
    single { FirebaseRepositoryImpl(get()) as IFirebaseRepository }
}

private fun provideFirebase(): FirebaseFirestore = FirebaseFirestore.getInstance()