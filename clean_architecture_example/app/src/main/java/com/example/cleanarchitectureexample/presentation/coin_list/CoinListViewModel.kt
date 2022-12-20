package com.example.cleanarchitectureexample.presentation.coin_list

import androidx.lifecycle.*
import com.example.cleanarchitectureexample.common.Resource
import com.example.cleanarchitectureexample.domain.use_case.get_coins.GetCoinsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.inject

class CoinListViewModel : ViewModel() {
    private val getCoinsUseCase: GetCoinsUseCase by inject(GetCoinsUseCase::class.java)
    private val _state = MutableLiveData(CoinListState())
    val state: LiveData<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when(result) {
                is  Resource.Success -> {
                    _state.value = CoinListState(
                        coins = result.data ?: emptyList()
                    )
                }
                is  Resource.Loading -> {
                    _state.value = CoinListState(
                        isLoading = true
                    )
                }
                is  Resource.Error -> {
                    _state.value = CoinListState(
                        error = result.message ?: "An unexpected error happened"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}