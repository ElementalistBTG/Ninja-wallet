package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins_params.GetCoinsParamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsParamsUseCase: GetCoinsParamsUseCase
) : ViewModel() {
    //viewmodels maintain our state
    // even after we moved the business logic on use cases

    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state: State<CoinListState> = _state

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()
    
    fun refresh() {
        viewModelScope.launch {
            getCoinsWithParams(
                currency = "usd",
                order = "market_cap_desc",
                per_page = 250,
                page = 1,
                price_change_percentage = "1h,24h,7d"
            )
        }
    }

    init {
        //getCoins()
        //getCoinsByMC()
        getCoinsWithParams(
            currency = "usd",
            order = "market_cap_desc",
            per_page = 250,
            page = 1,
            price_change_percentage = "1h,24h,7d"
        )
    }

    //all this fun does is execute our use case and put the result in a state object
//    private fun getCoins() {
//        //the class GetCoinsUseCase can be called as a function since we overwrite the invoke method
//        getCoinsUseCase().onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _state.value = CoinListState(coins = result.data ?: emptyList())
//                }
//                is Resource.Error -> {
//                    _state.value =
//                        CoinListState(error = result.message ?: "An unexpected error occured")
//                }
//                is Resource.Loading -> {
//                    _state.value = CoinListState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
//
//    }

    //all this fun does is execute our use case and put the result in a state object
//    private fun getCoinsByMC() {
//        //the class GetCoinsUseCase can be called as a function since we overwrite the invoke method
//        getCoinsMCUseCase().onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _state.value = CoinListState(coins = result.data ?: emptyList())
//                }
//                is Resource.Error -> {
//                    _state.value =
//                        CoinListState(error = result.message ?: "An unexpected error occured")
//                }
//                is Resource.Loading -> {
//                    _state.value = CoinListState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
//    }

    private fun getCoinsWithParams(
        currency: String,
        order: String,
        per_page: Int,
        page: Int,
        price_change_percentage: String
    ) {
        //the class GetCoinsUseCase can be called as a function since we overwrite the invoke method
        getCoinsParamsUseCase(
            currency = currency,
            order = order,
            per_page = per_page,
            page = page,
            price_change_percentage = price_change_percentage
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        CoinListState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
    }
}

