package com.plcoding.cryptocurrencyappyt.domain.use_case.add_coin_Watchlist


import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import javax.inject.Inject

class AddCoinWatchlistUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    //overwrite the invoke/execute fun -> So we can use the use case as it was a function
    suspend operator fun invoke(coin: CoinsEntity) {
        repository.addNewCoinToWatchlist(coin)
        //if the above line is successful we can emit the Resource.Success to our viewmodel

    }
}