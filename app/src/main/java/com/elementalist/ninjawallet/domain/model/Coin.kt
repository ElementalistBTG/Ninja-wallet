package com.elementalist.ninjawallet.domain.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val marketCap: Double,
    val priceChange1hr: Double,
    val priceChange24hr: Double,
    val priceChange7d: Double,
    val priceChange14d: Double,
    val priceChange30d: Double
)
