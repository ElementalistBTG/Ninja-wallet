package com.plcoding.cryptocurrencyappyt.data.repository

import com.plcoding.cryptocurrencyappyt.data.local.PortfolioDao
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.data.remote.CoinGeckoApi
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.CoinDetailDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsList.CoinsDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsListMC.CoinListMCDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.PortfolioCoinDetails.PortfolioCoinsDTO
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi,
    private val dao: PortfolioDao
) : CoinRepository {
    override suspend fun getCoins(): List<CoinsDTO> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coindId: String): CoinDetailDTO {
        return api.getCoinById(coindId)
    }

    override suspend fun getCoinsByMC(): CoinListMCDTO {
        return api.getCoinsByMC()
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

    override suspend fun getPortfolioCoinsFromDB(): List<CoinsEntity> {
        return dao.getPortfolioCoins()
    }

    override suspend fun getPortfolioCoinData(
        id: String,
    ): PortfolioCoinsDTO {
        return api.getPortfolioCoinParams(
            id = id
        )
    }

    override suspend fun addNewCoinToPortfolio(coin: CoinsEntity) {
        return dao.insertCoinToPortfolio(coin)
    }

    override suspend fun deleteCoinFromPortfolio(coin: CoinsEntity) {
        return dao.deleteCoinFromPortfolio(coin)
    }


}