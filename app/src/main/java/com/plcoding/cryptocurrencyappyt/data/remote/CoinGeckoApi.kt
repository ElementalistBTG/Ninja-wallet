package com.plcoding.cryptocurrencyappyt.data.remote


import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.CoinDetailDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsListMC.CoinListMCDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    //define the different routes and options we want to access from our api

    //get specific coin
    @GET("coins/{coinId}")
    suspend fun getCoinById(
        @Path("coinId") coinId: String,
        @Query("localization") localization: String = "false"
    ): CoinDetailDTO

    @GET("coins/markets")
    suspend fun getCoinsParams(
        @Query("vs_currency") currency: String,//The target currency of market data (usd, eur, jpy, etc.)
        @Query("order") order: String,//valid values: market_cap_desc, gecko_desc, gecko_asc, market_cap_asc, market_cap_desc, volume_asc, volume_desc, id_asc, id_desc
        @Query("per_page") per_page: Int,//valid values: 1..250
        //Total results per page
        //Default value : 100
        @Query("page") page: Int,//Page through results
        //Default value : 1
        @Query("price_change_percentage") price_change_percentage: String,
        //Include price change percentage in 1h, 24h, 7d, 14d, 30d, 200d, 1y (eg. '1h,24h,7d' comma-separated
        @Query("ids") ids: String = ""
        //ids to be added when retrieving Watchlist coins
    ): CoinListMCDTO


}
