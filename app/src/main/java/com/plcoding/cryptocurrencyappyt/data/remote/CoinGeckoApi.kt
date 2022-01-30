package com.plcoding.cryptocurrencyappyt.data.remote


import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.CoinDetailDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsList.CoinsDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsListMC.CoinListMCDTO
import com.plcoding.cryptocurrencyappyt.data.remote.dto.PortfolioCoinDetails.PortfolioCoinsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    //define the different routes and options we want to access from our api

    //get all coins
    @GET("coins/list")
    suspend fun getCoins(): List<CoinsDTO>

    //get specific coin
    @GET("coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDTO

    //get coins by market cap ranked
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc")
    suspend fun getCoinsByMC(): CoinListMCDTO

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
    ) : CoinListMCDTO

    @GET("simple/price")
    suspend fun getPortfolioCoinParams(
        @Query("ids") id: String,
        @Query("vs_currencies") currency: String = "usd",
        @Query("include_market_cap") marketCap: Boolean = true,
        @Query("include_24hr_change") change24hr: Boolean = true
    ) : PortfolioCoinsDTO

}
