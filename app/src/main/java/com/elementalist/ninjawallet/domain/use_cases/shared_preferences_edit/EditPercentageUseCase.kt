package com.elementalist.ninjawallet.domain.use_cases.shared_preferences_edit

import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import javax.inject.Inject

class EditPercentageUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(percentage: String) =
        repository.setPriceChangePercentage(percentage)
}