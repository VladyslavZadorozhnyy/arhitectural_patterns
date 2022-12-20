package com.example.cleanarchitectureexample.presentation.coin_list

import com.example.cleanarchitectureexample.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)