package com.elementalist.ninjawallet.data.remote.dto.CoinsListMC

import com.elementalist.ninjawallet.domain.model.Coin

data class CoinListMCDTOItem(
    val ath: Double,
    val ath_change_percentage: Double,
    val ath_date: String,
    val atl: Double,
    val atl_change_percentage: Double,
    val atl_date: String,
    val circulating_supply: Double,
    val current_price: Double,
    val fully_diluted_valuation: Double,
    val high_24h: Double,
    val id: String,
    val image: String,
    val last_updated: String,
    val low_24h: Double,
    val market_cap: Double,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val market_cap_rank: Int,
    val max_supply: Double,
    val name: String,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val price_change_percentage_14d_in_currency : Double,
    val price_change_percentage_1h_in_currency: Double,
    val price_change_percentage_24h_in_currency: Double,
    val price_change_percentage_30d_in_currency : Double,
    val price_change_percentage_7d_in_currency: Double,
    val roi: Roi,
    val symbol: String,
    val total_supply: Double,
    val total_volume: Double
)

fun CoinListMCDTOItem.toCoins(): Coin{
    return Coin(
        name = name,
        id = id,
        symbol = symbol,
        currentPrice = current_price,
        market_cap_rank = market_cap_rank,
        marketCap = market_cap,
        priceChange1hr = price_change_percentage_1h_in_currency,
        priceChange24hr = price_change_percentage_24h_in_currency,
        priceChange7d = price_change_percentage_7d_in_currency,
        priceChange14d = price_change_percentage_14d_in_currency,
        priceChange30d = price_change_percentage_30d_in_currency
    )
}