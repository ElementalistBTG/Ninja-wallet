package com.elementalist.ninjawallet.domain.use_cases.edit_coin_watchlist

import com.elementalist.ninjawallet.data.local.entity.CoinsEntity
import com.elementalist.ninjawallet.domain.repository.CoinRepository
import javax.inject.Inject

class RemoveCoinWatchlist @Inject constructor(
    private val repository: CoinRepository
) {
    //overwrite the invoke/execute fun -> So we can use the use case as it was a function
    suspend operator fun invoke(coin: CoinsEntity) {
        repository.deleteCoinFromWatchlist(coin)
        //if the above line is successful we can emit the Resource.Success to our viewmodel

    }
}