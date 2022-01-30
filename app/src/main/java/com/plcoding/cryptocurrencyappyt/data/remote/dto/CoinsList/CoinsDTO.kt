package com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsList

import com.plcoding.cryptocurrencyappyt.domain.model.Coin

data class CoinsDTO(
    val id: String,
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val marketCap: Double,
    val priceChange24hr: Double
)

fun CoinsDTO.toCoins(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        //priceChange24hr = (priceChange24hr * 100).roundToInt() /100.0,
        priceChange24hr = priceChange24hr,
        marketCap = marketCap,
        currentPrice = currentPrice
    )
}
