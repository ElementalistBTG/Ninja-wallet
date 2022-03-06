package com.elementalist.ninjawallet.presentation.Watchlist

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elementalist.ninjawallet.common.Constants.PRICE_CHANGE_PERCENTAGES
import com.elementalist.ninjawallet.common.Constants.curr2
import com.elementalist.ninjawallet.common.Resource
import com.elementalist.ninjawallet.data.local.Preferences.CURRENCY
import com.elementalist.ninjawallet.data.local.Preferences.PRICE_CHANGE_PERCENTAGE
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import com.elementalist.ninjawallet.domain.use_case.get_watchlist_coins.GetWatchlistCoinsDataUseCase
import com.elementalist.ninjawallet.presentation.components.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistCoinsDataUseCase: GetWatchlistCoinsDataUseCase,
    private val repository: PreferencesRepository
) : ViewModel() {

    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state: State<CoinListState> = _state

    private var _emptyList = mutableStateOf(false)
    val emptyList: State<Boolean> = _emptyList

    private var _percentageSelected = MutableLiveData<String>()
    val percentageSelected: LiveData<String> = _percentageSelected

    private var _currencySelected = MutableLiveData<String>()
    val currencySelected: LiveData<String> = _currencySelected

    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == PRICE_CHANGE_PERCENTAGE) {
                _percentageSelected.value = repository.getPriceChangePercentage()
            } else if (key == CURRENCY) {
                _currencySelected.value = repository.getCurrency()
                refresh()
            }
        }

    init {
        repository.pref.registerOnSharedPreferenceChangeListener(listener)
        refresh()
    }

    fun refresh() {
        _percentageSelected.value = repository.getPriceChangePercentage()
        _currencySelected.value = repository.getCurrency()
        getWatchlistCoins()
    }

    private fun getWatchlistCoins() {
        getWatchlistCoinsDataUseCase(
            currency = _currencySelected.value ?: curr2,
            per_page = 250,
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

    override fun onCleared() {
        repository.pref.unregisterOnSharedPreferenceChangeListener(listener)
        super.onCleared()
    }
}

