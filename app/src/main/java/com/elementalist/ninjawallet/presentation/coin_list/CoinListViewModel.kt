package com.elementalist.ninjawallet.presentation.coin_list

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
import com.elementalist.ninjawallet.domain.use_case.get_coins_params.GetCoinsParamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsParamsUseCase: GetCoinsParamsUseCase,
    private val repository: PreferencesRepository
) : ViewModel() {
    // viewmodels maintain our state
    // even after we moved the business logic on use cases

    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state: State<CoinListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private var _percentageSelected = MutableLiveData<String>()
    val percentageSelected: LiveData<String>
        get() = _percentageSelected

    private var _currencySelected = MutableLiveData<String>()
    val currencySelected: LiveData<String>
        get() = _currencySelected

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
        _currencySelected.value = repository.getCurrency()
        _percentageSelected.value = repository.getPriceChangePercentage()
        getCoinsWithParams(
            currency = _currencySelected.value ?: curr2,
            order = "market_cap_desc",
            per_page = 250,
            page = 1,
            price_change_percentage = PRICE_CHANGE_PERCENTAGES
        )
    }

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
        }
            .launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
    }

    override fun onCleared() {
        repository.pref.unregisterOnSharedPreferenceChangeListener(listener)
        super.onCleared()
    }
}

