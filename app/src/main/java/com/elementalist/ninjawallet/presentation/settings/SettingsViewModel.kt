package com.elementalist.ninjawallet.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: PreferencesRepository
) : ViewModel() {

    private var _percentageSelected = MutableLiveData<String>()
    val percentageSelected: LiveData<String> = _percentageSelected

    private var _currencySelected = MutableLiveData<String>()
    val currencySelected: LiveData<String> = _currencySelected

    private var _coinsEntered = MutableLiveData<Int>()
    val coinsEntered: LiveData<Int> = _coinsEntered

    init {
        _percentageSelected.value = repository.getPriceChangePercentage()
        _currencySelected.value = repository.getCurrency()
        _coinsEntered.value = repository.getCoinsDisplayedNumber()
    }

    fun percentageChange(percentage: String) {
        _percentageSelected.value = percentage
        repository.setPriceChangePercentage(percentage)
    }

    fun currencyChange(currency: String) {
        _currencySelected.value = currency
        repository.setCurrency(currency)
    }

    fun coinNumberChange(coins: Int){
        _coinsEntered.value = coins
        repository.setCoinsDisplayedNumber(coins)
    }



}

