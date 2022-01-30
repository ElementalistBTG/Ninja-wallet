package com.plcoding.cryptocurrencyappyt.data.remote.dto.PortfolioCoinDetails

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioCoinItem(
    val usd: Double,
    val usd_market_cap: Double,
    val usd_24h_change: Double
)
