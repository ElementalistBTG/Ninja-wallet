package com.elementalist.ninjawallet.presentation.settings

import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import com.elementalist.ninjawallet.presentation.ViewModelWithSharedPreferencesAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    repository: PreferencesRepository
) : ViewModelWithSharedPreferencesAccess(repository)


