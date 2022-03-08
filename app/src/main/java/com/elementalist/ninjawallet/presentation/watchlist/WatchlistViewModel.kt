package com.elementalist.ninjawallet.presentation.watchlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.elementalist.ninjawallet.common.Constants.curr2
import com.elementalist.ninjawallet.common.Resource
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import com.elementalist.ninjawallet.domain.use_cases.get_watchlist_coins.GetWatchlistCoinsDataUseCase
import com.elementalist.ninjawallet.presentation.ViewModelWithSharedPreferencesAccess
import com.elementalist.ninjawallet.presentation.components.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistCoinsDataUseCase: GetWatchlistCoinsDataUseCase,
    repository: PreferencesRepository
) : ViewModelWithSharedPreferencesAccess(repository) {

    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    private var _emptyList = mutableStateOf(false)
    val emptyList: State<Boolean> = _emptyList

    init {
        refresh()
    }

    override fun refresh() {
        getWatchlistCoins()
    }

    private fun getWatchlistCoins() {
        getWatchlistCoinsDataUseCase(
            currency = currencySelected.value ?: curr2,
            per_page = coinsEntered.value ?: 250,
            page = 1
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                    _emptyList.value = _state.value.coins.isEmpty()
                }
                is Resource.Error -> {
                    _state.value =
                        CoinListState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
            }
        }
            .launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
    }

}

