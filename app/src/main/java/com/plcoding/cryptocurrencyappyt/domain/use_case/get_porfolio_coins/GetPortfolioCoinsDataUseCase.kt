package com.plcoding.cryptocurrencyappyt.domain.use_case.get_porfolio_coins


import android.util.Log
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPortfolioCoinsDataUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    //overwrite the invoke/execute fun -> So we can use the use case as it was a function
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            //we first emit a loading status
            emit(Resource.Loading<List<Coin>>())

            val coinEntities = repository.getPortfolioCoinsFromDB()
            val coins = mutableListOf<Coin>()
            for (coinEntity in coinEntities) {
                val portfolioItems = repository.getPortfolioCoinData(coinEntity.id)
//                Log.d("mytag",portfolioItems::coin.name.toString())
                coins.add(
                    Coin(
                        id = coinEntity.id,
                        name = coinEntity.name,
                        symbol = coinEntity.symbol,
                        currentPrice = portfolioItems.bitcoin.usd,
                        priceChange24hr = portfolioItems.bitcoin.usd_24h_change,
                        marketCap = portfolioItems.bitcoin.usd_market_cap
                    )
                )
                Log.d("mytag",portfolioItems.toString())
            }

            //if the above line is successful we can emit the Resource.Success to our viewmodel
            emit(Resource.Success<List<Coin>>(coins))
        } catch (e: HttpException) {
            //if we get an http response code that doesn't start with a 2XX
            Log.d("mytag", e.localizedMessage)
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Coin>>("Couldn't reach server, check your internet connection"))
        }
    }
}