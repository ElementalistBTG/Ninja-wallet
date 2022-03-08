package com.elementalist.ninjawallet.presentation.settings

import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import com.elementalist.ninjawallet.domain.use_cases.shared_preferences_edit.EditCoinNumberUseCase
import com.elementalist.ninjawallet.domain.use_cases.shared_preferences_edit.EditCurrencyUseCase
import com.elementalist.ninjawallet.domain.use_cases.shared_preferences_edit.EditPercentageUseCase
import com.elementalist.ninjawallet.presentation.ViewModelWithSharedPreferencesAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    repository: PreferencesRepository,
    private val editPercentageUseCase: EditPercentageUseCase,
    private val editCoinNumberUseCase: EditCoinNumberUseCase,
    private val editCurrencyUseCase: EditCurrencyUseCase
) : ViewModelWithSharedPreferencesAccess(repository) {

    override fun percentageChange(percentage: String) {
        super.percentageChange(percentage)
        editPercentageUseCase(percentage)
    }

    override fun currencyChange(currency: String) {
        super.currencyChange(currency)
        editCurrencyUseCase(currency)
    }

    override fun coinNumberChange(coins: Int) {
        super.coinNumberChange(coins)
        editCoinNumberUseCase(coins)
    }
}


