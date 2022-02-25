package com.plcoding.cryptocurrencyappyt.domain.repository

import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.CoinDetailDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsList.CoinsDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsListMC.CoinListMCDTO

interface CoinRepository {

    suspend fun getCoinById(coindId: String): CoinDetailDTO

    suspend fun getCoinsParams(
        currency: String,
        order: String,
        per_page: Int,
        page: Int,
        price_change_percentage: String
    ): CoinListMCDTO

    suspend fun getPortfolioCoinsFromDB(): List<CoinsEntity>

    suspend fun getPortfolioCoinData(
        ids: String,
        currency: String,
        order: String,
        per_page: Int,
        page: Int,
        price_change_percentage: String
    ): CoinListMCDTO

    suspend fun addNewCoinToPortfolio(coin: CoinsEntity)

    suspend fun deleteCoinFromPortfolio(coin: CoinsEntity)
}