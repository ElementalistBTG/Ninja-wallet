package com.elementalist.ninjawallet.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.elementalist.ninjawallet.common.Constants
import com.elementalist.ninjawallet.common.Resource
import com.elementalist.ninjawallet.data.local.entity.CoinsEntity
import com.elementalist.ninjawallet.domain.use_cases.add_coin_Watchlist.AddCoinWatchlistUseCase
import com.elementalist.ninjawallet.domain.use_cases.edit_coin_watchlist.CheckCoinExistsUseCase
import com.elementalist.ninjawallet.domain.use_cases.get_coin.GetCoinUseCase
import com.elementalist.ninjawallet.domain.use_cases.edit_coin_watchlist.RemoveCoinWatchlist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val addCoinWatchlistUseCase: AddCoinWatchlistUseCase,
    private val removeCoinWatchList: RemoveCoinWatchlist,
    private val checkCoinExistsUseCase: CheckCoinExistsUseCase,
    savedStateHandle: SavedStateHandle //use it to restore our app for process death for example/ also contains the navigation parameters
) : ViewModel() {
    //ViewModels maintain our state
    // even after we moved the business logic on use cases

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    private var _exists = MutableLiveData<Boolean>()
    val exists: LiveData<Boolean> = _exists

    private fun checkCoinExists(coinId: String) {
        viewModelScope.launch {
            _exists.value = checkCoinExistsUseCase(coinId)
        }
    }

    fun addCoinToWatchlist(coin: CoinsEntity) {
        viewModelScope.launch {
            addCoinWatchlistUseCase(coin)
            _exists.value = true
        }
    }

    fun removeCoinFromWatchlist(coin: CoinsEntity) {
        viewModelScope.launch {
            removeCoinWatchList(coin)
            _exists.value = false
        }
    }

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)
            ?.let { coinId -> //check if it not equal to null
                getCoin(coinId)
                checkCoinExists(coinId)
            }
    }

    //all this fun does is execute our use case and put the result in a state object
    private fun getCoin(coinId: String) {
        //the class GetCoinsUseCase can be called as a function since we overwrite the invoke method
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinDetailState(coin = result.data)
                }
                is Resource.Error -> {
                    _state.value =
                        CoinDetailState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
                }
            }
        }
            .launchIn(viewModelScope)// returns the flow that emits the resource values over time. We must launch it as a coroutine!
    }
}

