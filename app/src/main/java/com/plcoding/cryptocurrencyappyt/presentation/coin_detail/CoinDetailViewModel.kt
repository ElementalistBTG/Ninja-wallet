package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.domain.use_case.add_coin_portfolio.AddCoinPortfolioUseCase
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val addCoinPortfolioUseCase: AddCoinPortfolioUseCase,
    savedStateHandle: SavedStateHandle //use it to restore our app for process death for example/ also contains the navigation parameters
) : ViewModel() {
    //viewmodels maintain our state
    // even after we moved the business logic on use cases

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    fun addCoinToPorfolio(coin: CoinsEntity){
        viewModelScope.launch {
            addCoinPortfolioUseCase(coin)
        }
    }

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)
            ?.let { coinId -> //check if it not equal to null
                getCoin(coinId)
            }
    }

    //all this fun does is execute our use case and put the result in a state object
    private fun getCoin(coinId: String) {
        //the class GetCoinsUseCase can be called as a function since we overwrited the invoke method
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinDetailState(coin = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        CoinDetailState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
    }
}
