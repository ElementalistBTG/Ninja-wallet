package com.plcoding.cryptocurrencyappyt.presentation.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plcoding.cryptocurrencyappyt.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val repository: PreferencesRepository
) : ViewModel() {

    private var _percentageSelected = MutableLiveData<String>()
    val percentageSelected: LiveData<String>
        get() = _percentageSelected



    init {
        _percentageSelected.value = repository.getPriceChangePercentage()
    }

    fun percentageChange(percentage: String) {
        _percentageSelected.value = percentage
        repository.setPriceChangePercentage(percentage)
    }




}

