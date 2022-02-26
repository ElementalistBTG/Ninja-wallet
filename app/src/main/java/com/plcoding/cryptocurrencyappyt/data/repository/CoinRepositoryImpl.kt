package com.plcoding.cryptocurrencyappyt.data.repository

import com.plcoding.cryptocurrencyappyt.data.local.WatchlistDao
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.data.remote.CoinGeckoApi
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.CoinDetailDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsListMC.CoinListMCDTO
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi,
    private val dao: WatchlistDao
) : CoinRepository {

    override suspend fun getCoinById(coindId: String): CoinDetailDTO {
        return api.getCoinById(coindId)
    }

    override suspend fun getCoinsParams(
        currency: String,
        order: String,
        per_page: Int,
        page: Int,
        price_change_percentage: String
    ): CoinListMCDTO {
        return api.getCoinsParams(
            currency = currency,
            order = order,
            per_page = per_page,
            page = page,
            price_change_percentage = price_change_percentage
        )
    }

    override suspend fun getWatchlistCoinsFromDB(): List<CoinsEntity> {
        return dao.getWatchlistCoins()
    }

    override suspend fun getWatchlistCoinData(
        ids: String,
        currency: String,
        order: String,
        per_page: Int,
        page: Int,
        price_change_percentage: String
    ): CoinListMCDTO {
        return api.getCoinsParams(
            ids = ids,
            currency = currency,
            order = order,
            per_page = per_page,
            page = page,
            price_change_percentage = price_change_percentage
        )
    }

    override suspend fun addNewCoinToWatchlist(coin: CoinsEntity) {
        return dao.insertCoinToWatchlist(coin)
    }

    override suspend fun deleteCoinFromWatchlist(coin: CoinsEntity) {
        return dao.deleteCoinFromWatchlist(coin)
    }


}