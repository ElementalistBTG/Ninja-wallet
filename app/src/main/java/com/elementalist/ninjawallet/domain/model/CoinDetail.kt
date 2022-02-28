package com.elementalist.ninjawallet.domain.model

import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.Description
import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.Image
import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.Links
import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.MarketData

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
