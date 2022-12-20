package com.example.cleanarchitectureexample.domain.repository

import com.example.cleanarchitectureexample.data.remote.dto.CoinDetailDto
import com.example.cleanarchitectureexample.data.remote.dto.CoinDto

interface CoinRepository {
//    It has one method for one API call as well
//    as additional functionality: caching etc

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinDetails(coinId: String): CoinDetailDto
}