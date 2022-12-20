package com.example.cleanarchitectureexample.presentation.coin_details

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureexample.common.Constants
import com.example.cleanarchitectureexample.common.Resource
import com.example.cleanarchitectureexample.domain.model.CoinDetail
import com.example.cleanarchitectureexample.domain.use_case.get_coin.GetCoinUseCase
import com.example.cleanarchitectureexample.domain.use_case.get_coins.GetCoinsUseCase
import com.example.cleanarchitectureexample.presentation.coin_list.CoinListState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.inject

class CoinDetailsViewModel(
    private val arguments: Bundle
) : ViewModel() {
    private val getCoinUseCase: GetCoinUseCase by inject(GetCoinUseCase::class.java)
    private val _state = MutableLiveData(CoinDetailsState())
    val state: LiveData<CoinDetailsState> = _state

    init {
        getCoinDetails(arguments.getString(Constants.PARAM_COIN_ID, ""))
    }

    private fun getCoinDetails(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when(result) {
                is  Resource.Success -> {
                    _state.value = CoinDetailsState(
                        coinDetail = result.data
                    )
                }
                is  Resource.Loading -> {
                    _state.value = CoinDetailsState(
                        isLoading = true
                    )
                }
                is  Resource.Error -> {
                    _state.value = CoinDetailsState(
                        error = result.message ?: "An unexpected error happened"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}