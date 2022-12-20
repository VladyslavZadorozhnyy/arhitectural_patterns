package com.example.cleanarchitectureexample.di

import com.example.cleanarchitectureexample.common.Constants
import com.example.cleanarchitectureexample.data.remote.CoinPaprikaApi
import com.example.cleanarchitectureexample.data.repository.CoinRepositoryImpl
import com.example.cleanarchitectureexample.domain.repository.CoinRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    single<CoinRepository> {
        CoinRepositoryImpl(get())
    }
}