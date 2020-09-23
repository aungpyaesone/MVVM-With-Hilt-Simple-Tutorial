package com.aungpyaesone.hilt_demo_app.di

import android.content.Context
import com.aungpyaesone.hilt_demo_app.BuildConfig
import com.aungpyaesone.hilt_demo_app.datasource.CharacterRemoteDataSource
import com.aungpyaesone.hilt_demo_app.network.CharacterService
import com.aungpyaesone.hilt_demo_app.persistence.AppDatabase
import com.aungpyaesone.hilt_demo_app.persistence.CharacterDao
import com.aungpyaesone.hilt_demo_app.repo.CharacterRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .apply {
            if (BuildConfig.DEBUG) client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
        }
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideCharacterRepository(characterRemoteDataSource: CharacterRemoteDataSource,
    characterDao: CharacterDao) = CharacterRepository(characterRemoteDataSource,characterDao)

    @Singleton
    @Provides
    fun provideCharacterDao(db:AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context:Context) = AppDatabase.getDatabase(context)
}