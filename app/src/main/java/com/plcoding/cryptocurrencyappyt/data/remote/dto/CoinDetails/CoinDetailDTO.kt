package com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails

import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail

data class CoinDetailDTO(
    val additional_notices: List<Any>,
    val asset_platform_id: Any,
    val block_time_in_minutes: Int,
    val categories: List<Any>,
    val coingecko_rank: Int,
    val coingecko_score: Double,
    val community_data: CommunityData,
    val community_score: Double,
    val country_origin: String,
    val description: Description,
    val developer_data: DeveloperData,
    val developer_score: Double,
    val genesis_date: Any,
    val hashing_algorithm: Any,
    val id: String,
    val image: Image,
    val last_updated: String,
    val links: Links,
    val liquidity_score: Double,
    val localization: Localization,
    val market_cap_rank: Int,
    val market_data: MarketData,
    val name: String,
    val platforms: Platforms,
    val public_interest_score: Double,
    val public_interest_stats: PublicInterestStats,
    val public_notice: Any,
    val sentiment_votes_down_percentage: Double,
    val sentiment_votes_up_percentage: Double,
    val status_updates: List<StatusUpdate>,
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
