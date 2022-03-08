package com.elementalist.ninjawallet.domain.use_cases.shared_preferences_edit

import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import javax.inject.Inject

class EditCurrencyUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(currency: String) =
        repository.setCurrency(currency)
}