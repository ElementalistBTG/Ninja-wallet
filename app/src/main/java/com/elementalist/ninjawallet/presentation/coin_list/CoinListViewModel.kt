package com.elementalist.ninjawallet.presentation.coin_list

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elementalist.ninjawallet.common.Constants.curr2
import com.elementalist.ninjawallet.common.Resource
import com.elementalist.ninjawallet.data.local.Preferences.COINS_DISPLAYED
import com.elementalist.ninjawallet.data.local.Preferences.CURRENCY
import com.elementalist.ninjawallet.data.local.Preferences.PRICE_CHANGE_PERCENTAGE
import com.elementalist.ninjawallet.domain.model.Coin
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import com.elementalist.ninjawallet.domain.use_case.get_coins_params.GetCoinsParamsUseCase
import com.elementalist.ninjawallet.presentation.components.CoinListState
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

    var searchTyped by mutableStateOf(TextFieldValue(""))
        private set

//    var state by mutableStateOf<CoinListState>(CoinListState())
//        private set

    //can be implemented with just state
    private val _myState = MutableStateFlow(CoinListState())
    val myState: StateFlow<CoinListState> = _myState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private var _percentageSelected = MutableLiveData<String>()
    val percentageSelected: LiveData<String>
        get() = _percentageSelected

    private var _currencySelected = MutableLiveData<String>()
    val currencySelected: LiveData<String>
        get() = _currencySelected

    private var coinsEntered = 250
    private var pages = 1


    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                PRICE_CHANGE_PERCENTAGE -> {
                    _percentageSelected.value = repository.getPriceChangePercentage()
                }
                CURRENCY -> {
                    _currencySelected.value = repository.getCurrency()
                    refresh()
                }
                COINS_DISPLAYED -> {
                    coinsEntered = repository.getCoinsDisplayedNumber()
                    refresh()
                }
            }
        }

    init {
        repository.pref.registerOnSharedPreferenceChangeListener(listener)
        _currencySelected.value = repository.getCurrency()
        _percentageSelected.value = repository.getPriceChangePercentage()
        coinsEntered = repository.getCoinsDisplayedNumber()
        refresh()
    }

    fun refresh() {
        if (coinsEntered > 250) {
            //to be solved on a later date
            coinsEntered = 250
        }
        getCoinsWithParams(
            currency = _currencySelected.value ?: curr2,
            per_page = coinsEntered,
            page = 1
        )
    }

    private fun getCoinsWithParams(
        currency: String,
        per_page: Int,
        page: Int
    ) {
        //the class GetCoinsUseCase can be called as a function since we overwrite the invoke method
        getCoinsParamsUseCase(
            currency = currency,
            per_page = per_page,
            page = page
        ).onEach { result ->
            _myState.value = when (result) {
                is Resource.Success -> {
                    CoinListState(coins =
                    result.data?.filter { coin: Coin ->
                        coin.name.contains(searchTyped.text, ignoreCase = true)
                    } ?: emptyList())
                }
                is Resource.Error -> {
                    CoinListState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    CoinListState(isLoading = true)
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

