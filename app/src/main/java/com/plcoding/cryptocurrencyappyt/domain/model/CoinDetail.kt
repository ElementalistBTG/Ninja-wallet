package com.plcoding.cryptocurrencyappyt.domain.model

import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.Description
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.Image
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.Links
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.MarketData

data class CoinDetail(
    val description: Description,
    val id: String,
    val image: Image,
    val links: Links,
    val market_cap_rank: Int,
    val market_data: MarketData,
    val name: String,
    val symbol: String
)
