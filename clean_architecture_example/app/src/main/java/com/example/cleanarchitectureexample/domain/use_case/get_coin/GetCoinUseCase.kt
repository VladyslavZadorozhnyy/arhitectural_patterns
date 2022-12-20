package com.example.cleanarchitectureexample.domain.use_case.get_coin

import com.example.cleanarchitectureexample.common.Resource
import com.example.cleanarchitectureexample.data.remote.dto.toCoinDetail
import com.example.cleanarchitectureexample.domain.model.CoinDetail
import com.example.cleanarchitectureexample.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.io.IOException

class GetCoinUseCase {
    private val repository: CoinRepository by inject(CoinRepository::class.java)

    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinDetails(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error happened"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection"))
        }
    }
}