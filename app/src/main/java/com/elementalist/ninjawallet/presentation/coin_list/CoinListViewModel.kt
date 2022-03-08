package com.elementalist.ninjawallet.presentation.coin_list

import androidx.lifecycle.viewModelScope
import com.elementalist.ninjawallet.common.Constants.curr2
import com.elementalist.ninjawallet.common.Resource
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
import com.elementalist.ninjawallet.domain.use_cases.get_coins_params.GetCoinsParamsUseCase
import com.elementalist.ninjawallet.presentation.ViewModelWithSharedPreferencesAccess
import com.elementalist.ninjawallet.presentation.components.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsParamsUseCase: GetCoinsParamsUseCase,
    repository: PreferencesRepository
) : ViewModelWithSharedPreferencesAccess(repository) {
    // viewmodels maintain our state
    // even after we moved the business logic on use cases

    //can be implemented with just state (tried this approach for experimentation)
    private val _myState = MutableStateFlow(CoinListState())
    val myState: StateFlow<CoinListState> = _myState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        refresh()
    }

    override fun refresh() {
        getCoinsWithParams(
            currency = currencySelected.value ?: curr2,
            per_page = coinsEntered.value ?: 250,
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
                    CoinListState(
                        coins = result.data ?: emptyList()
                    )
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
}

