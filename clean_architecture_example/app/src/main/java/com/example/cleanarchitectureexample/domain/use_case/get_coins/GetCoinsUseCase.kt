package com.example.cleanarchitectureexample.domain.use_case.get_coins

import com.example.cleanarchitectureexample.common.Resource
import com.example.cleanarchitectureexample.data.remote.dto.toCoin
import com.example.cleanarchitectureexample.domain.model.Coin
import com.example.cleanarchitectureexample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.io.IOException

class GetCoinsUseCase {
    private val repository: CoinRepository by inject(CoinRepository::class.java)

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection"))
        }
    }
}