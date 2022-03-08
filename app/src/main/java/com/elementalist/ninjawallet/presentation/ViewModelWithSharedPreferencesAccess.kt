package com.elementalist.ninjawallet.presentation

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elementalist.ninjawallet.data.local.Preferences.COINS_DISPLAYED
import com.elementalist.ninjawallet.data.local.Preferences.CURRENCY
import com.elementalist.ninjawallet.data.local.Preferences.PRICE_CHANGE_PERCENTAGE
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class ViewModelWithSharedPreferencesAccess @Inject constructor(
    private val repository: PreferencesRepository
) : ViewModel() {

    private var _percentageSelected = MutableLiveData<String>()
    val percentageSelected: LiveData<String> = _percentageSelected

    private var _currencySelected = MutableLiveData<String>()
    val currencySelected: LiveData<String> = _currencySelected

    private var _coinsEntered = MutableLiveData<Int>()
    val coinsEntered: LiveData<Int> = _coinsEntered

    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                PRICE_CHANGE_PERCENTAGE -> {
                    _percentageSelected.value = repository.getPriceChangePercentage()
                }
                CURRENCY -> {
                    _currencySelected.value = repository.getCurrency()
                    refresh()
                }
                COINS_DISPLAYED -> {
                    _coinsEntered.value = repository.getCoinsDisplayedNumber()
                    refresh()
                }
            }
        }

    init {
        repository.pref.registerOnSharedPreferenceChangeListener(listener)
        _percentageSelected.value = repository.getPriceChangePercentage()
        _currencySelected.value = repository.getCurrency()
        _coinsEntered.value = repository.getCoinsDisplayedNumber()
    }

    open fun percentageChange(percentage: String) {
        _percentageSelected.value = percentage
    }

    open fun currencyChange(currency: String) {
        _currencySelected.value = currency
    }

    open fun coinNumberChange(coins: Int) {
        _coinsEntered.value = coins
    }

    open fun refresh() {}

    override fun onCleared() {
        repository.pref.unregisterOnSharedPreferenceChangeListener(listener)
        super.onCleared()
    }
}
