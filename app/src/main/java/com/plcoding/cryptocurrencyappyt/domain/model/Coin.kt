package com.plcoding.cryptocurrencyappyt.domain.model

import com.google.gson.annotations.SerializedName
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.CurrentPrice
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.MarketCap

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val marketCap: Double,
    val priceChange24hr: Double
){
    fun toCoinEntity() : CoinsEntity{
        return CoinsEntity(
            id = id
        )
    }
}
