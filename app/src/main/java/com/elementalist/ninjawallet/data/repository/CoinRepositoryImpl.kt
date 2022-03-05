package com.elementalist.ninjawallet.data.repository

import com.elementalist.ninjawallet.data.local.WatchlistDao
import com.elementalist.ninjawallet.data.local.entity.CoinsEntity
import com.elementalist.ninjawallet.data.remote.CoinGeckoApi
import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.CoinDetailDTO
import com.elementalist.ninjawallet.data.remote.dto.CoinsListMC.CoinListMCDTO
import com.elementalist.ninjawallet.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi,
    private val dao: WatchlistDao
) : CoinRepository {

    override suspend fun checkCoinExists(coindId: String): Boolean {
        return dao.exists(coindId)
    }

    override suspend fun getCoinById(coindId: String): CoinDetailDTO {
        return api.getCoinById(coindId)
    }

    override suspend fun getCoinsParams(
        currency: String,
        per_page: Int,
        page: Int,
    ): CoinListMCDTO {
        return api.getCoinsParams(
            currency = currency,
            per_page = per_page,
            page = page
        )
    }

    override suspend fun getWatchlistCoinsFromDB(): List<CoinsEntity> {
        return dao.getWatchlistCoins()
    }

    override suspend fun getWatchlistCoinData(
        ids: String,
        currency: String,
        per_page: Int,
        page: Int
    ): CoinListMCDTO {
        return api.getCoinsParams(
            ids = ids,
            currency = currency,
            per_page = per_page,
            page = page
        )
    }

    override suspend fun addNewCoinToWatchlist(coin: CoinsEntity) {
        return dao.insertCoinToWatchlist(coin)
    }

    override suspend fun deleteCoinFromWatchlist(coin: CoinsEntity) {
        return dao.deleteCoinFromWatchlist(coin)
    }


}