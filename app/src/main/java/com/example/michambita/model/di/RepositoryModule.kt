package com.example.michambita.model.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {


    @Provides
    fun provideTrabajadorRepository(
        firestore: FirebaseFirestore
    ): TrabajadorRepository = TrabajadorRepositoryImpl(firestore)
}