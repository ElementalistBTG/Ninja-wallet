package com.elementalist.ninjawallet.domain.use_cases.edit_coin_watchlist

import com.elementalist.ninjawallet.domain.repository.CoinRepository
import javax.inject.Inject

class CheckCoinExistsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(coinId: String) = repository.checkCoinExists(coinId)
}