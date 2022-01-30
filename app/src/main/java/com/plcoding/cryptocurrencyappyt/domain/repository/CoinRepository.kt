package com.plcoding.cryptocurrencyappyt.domain.repository

import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.CoinDetailDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsList.CoinsDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsListMC.CoinListMCDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.PortfolioCoinDetails.PortfolioCoinsDTO

interface CoinRepository {

    suspend fun getCoins(): List<CoinsDTO>

    suspend fun getCoinById(coindId: String): CoinDetailDTO

    suspend fun getCoinsByMC(): CoinListMCDTO

    suspend fun getCoinsParams(
        currency: String,
        order: String,
        per_page: Int,
        page: Int,
        price_change_percentage: String
    ): CoinListMCDTO

    suspend fun getPortfolioCoinsFromDB(): List<CoinsEntity>

    suspend fun getPortfolioCoinData(
        id: String
    ): PortfolioCoinsDTO

    suspend fun addNewCoinToPortfolio(coin: CoinsEntity)

    suspend fun deleteCoinFromPortfolio(coin: CoinsEntity)
}