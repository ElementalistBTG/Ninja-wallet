package com.elementalist.ninjawallet.domain.repository

import com.elementalist.ninjawallet.data.local.entity.CoinsEntity
import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.CoinDetailDTO
import com.elementalist.ninjawallet.data.remote.dto.CoinsListMC.CoinListMCDTO

interface CoinRepository {

    suspend fun checkCoinExists(coindId: String): Boolean

    suspend fun getCoinById(coindId: String): CoinDetailDTO

    suspend fun getCoinsParams(
        currency: String,
        per_page: Int,
        page: Int
    ): CoinListMCDTO

    suspend fun getWatchlistCoinsFromDB(): List<CoinsEntity>

    suspend fun getWatchlistCoinData(
        ids: String,
        currency: String,
        per_page: Int,
        page: Int
    ): CoinListMCDTO

    suspend fun addNewCoinToWatchlist(coin: CoinsEntity)

    suspend fun deleteCoinFromWatchlist(coin: CoinsEntity)
}