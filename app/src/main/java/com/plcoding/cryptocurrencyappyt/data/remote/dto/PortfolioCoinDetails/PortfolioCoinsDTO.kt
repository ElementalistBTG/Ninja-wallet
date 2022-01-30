package com.plcoding.cryptocurrencyappyt.data.remote.dto.PortfolioCoinDetails

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class PortfolioCoinsDTO(
    val coin : PortfolioCoinItem
){
//    fun deserializeResponse(){
//        val obj = Json.decodeFromString<PortfolioCoinsDTO>()
//    }
}