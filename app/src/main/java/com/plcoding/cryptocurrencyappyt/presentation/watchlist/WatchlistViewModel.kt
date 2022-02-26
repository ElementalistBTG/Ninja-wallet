package com.plcoding.cryptocurrencyappyt.presentation.Watchlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_watchlist_coins.GetWatchlistCoinsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistCoinsDataUseCase: GetWatchlistCoinsDataUseCase
) : ViewModel() {

    private val _state = mutableStateOf<WatchlistState>(WatchlistState())
    val state: State<WatchlistState> = _state

    private var _emptyList = mutableStateOf(false)
    val emptyList: State<Boolean>
        get() = _emptyList

    init {
        getWatchlistCoins()
    }

    private fun getWatchlistCoins() {
        getWatchlistCoinsDataUseCase(
            currency = "usd",
            order = "market_cap_desc",
            per_page = 250,
            page = 1,
            price_change_percentage = "1h,24h,7d"
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WatchlistState(coins = result.data ?: emptyList())
                    _emptyList.value = _state.value.coins.isEmpty()
                }
                is Resource.Error -> {
                    _state.value =
                        WatchlistState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = WatchlistState(isLoading = true)
                }
            }
        }
            .launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
    }
}

