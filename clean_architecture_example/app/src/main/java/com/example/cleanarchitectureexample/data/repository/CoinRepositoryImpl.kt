package com.example.cleanarchitectureexample.data.repository

import com.example.cleanarchitectureexample.data.remote.CoinPaprikaApi
import com.example.cleanarchitectureexample.data.remote.dto.CoinDetailDto
import com.example.cleanarchitectureexample.data.remote.dto.CoinDto
import com.example.cleanarchitectureexample.domain.repository.CoinRepository
import org.koin.java.KoinJavaComponent.inject

class CoinRepositoryImpl(
    private val api: CoinPaprikaApi
): CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinDetails(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}