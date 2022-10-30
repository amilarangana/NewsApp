package com.amila.newsapp.di

import android.content.Context
import com.amila.newsapp.api.RetrofitInstance
import com.amila.newsapp.db.NewsDatabase
import com.amila.newsapp.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

   @Provides
   fun provideRetrofitInstance() =
       RetrofitInstance()

    @Provides
    fun provideNewsRepository(retrofitInstance: RetrofitInstance) =
        NewsRepository(retrofitInstance)

    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context) =
        NewsDatabase(context)
}