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
    val percentageSelected: LiveData<String>
        get() = _percentageSelected

    private var _currencySelected = MutableLiveData<String>()
    val currencySelected: LiveData<String>
        get() = _currencySelected

    private var _coinsEntered = MutableLiveData<String>()
    val coinsEntered: LiveData<String>
        get() = _coinsEntered


    init {
        _percentageSelected.value = repository.getPriceChangePercentage()
        _currencySelected.value = repository.getCurrency()
    }

    fun percentageChange(percentage: String) {
        _percentageSelected.value = percentage
        repository.setPriceChangePercentage(percentage)
    }

    fun currencyChange(currency: String) {
        _currencySelected.value = currency
        repository.setCurrency(currency)
    }

    fun coinNumberChange(coins: String){
        _coinsEntered.value = coins
    }



}

