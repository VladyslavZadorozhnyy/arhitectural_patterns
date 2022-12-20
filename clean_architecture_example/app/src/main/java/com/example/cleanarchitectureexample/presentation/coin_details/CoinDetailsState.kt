package com.example.cleanarchitectureexample.presentation.coin_details

import com.example.cleanarchitectureexample.domain.model.Coin
import com.example.cleanarchitectureexample.domain.model.CoinDetail

data class CoinDetailsState(
    val isLoading: Boolean = false,
    val coinDetail: CoinDetail? = null,
    val error: String = ""
)