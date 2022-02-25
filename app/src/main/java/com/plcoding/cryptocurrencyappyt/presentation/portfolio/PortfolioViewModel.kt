package com.plcoding.cryptocurrencyappyt.presentation.portfolio

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_porfolio_coins.GetPortfolioCoinsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getPortfolioCoinsDataUseCase: GetPortfolioCoinsDataUseCase
) : ViewModel() {

    private val _state = mutableStateOf<PortfolioState>(PortfolioState())
    val state: State<PortfolioState> = _state

    init {
        getPortfolioCoins()
    }

    private fun getPortfolioCoins() {
        getPortfolioCoinsDataUseCase(
            currency = "usd",
            order = "market_cap_desc",
            per_page = 250,
            page = 1,
            price_change_percentage = "1h,24h,7d"
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PortfolioState(coins = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        PortfolioState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = PortfolioState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
    }
}

