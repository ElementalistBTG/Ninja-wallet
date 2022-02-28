package com.elementalist.ninjawallet.data.remote.dto.CoinDetails

import com.elementalist.ninjawallet.domain.model.CoinDetail

data class CoinDetailDTO(
    val description: Description,
    val id: String,
    val image: Image,
    val last_updated: String,
    val links: Links,
    val market_cap_rank: Int,
    val market_data: MarketData,
    val name: String,
    val symbol: String,
    val tickers: List<Ticker>
)

fun CoinDetailDTO.toCoinDetail() : CoinDetail{
    return  CoinDetail(
        description = description,
        id = id,
        image = image,
        links = links,
        market_cap_rank = market_cap_rank,
        market_data = market_data,
        name = name,
        symbol = symbol
    )
}
