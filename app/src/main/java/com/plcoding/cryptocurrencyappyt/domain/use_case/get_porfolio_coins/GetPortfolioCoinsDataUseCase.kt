package com.plcoding.cryptocurrencyappyt.domain.use_case.get_porfolio_coins


import android.util.Log
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinsListMC.toCoins
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
    operator fun invoke(
        currency: String,
        order: String,
        per_page: Int,
        page: Int,
        price_change_percentage: String
    ): Flow<Resource<List<Coin>>> = flow {
        try {
            //we first emit a loading status
            emit(Resource.Loading<List<Coin>>())

            val coinEntities = repository.getPortfolioCoinsFromDB()
            var coinIds = coinEntities.first().id
            for (coinEntity in coinEntities) {
                if(coinEntity!=coinEntities.first()){
                    coinIds += ",${coinEntity.id}"
                }
            }
            val portfolioItems = repository.getPortfolioCoinData(
                ids = coinIds,
                currency = currency,
                order = order,
                per_page = per_page,
                page = page,
                price_change_percentage = price_change_percentage
            ).map { it.toCoins() }

            //if the above line is successful we can emit the Resource.Success to our viewmodel
            emit(Resource.Success<List<Coin>>(portfolioItems))
        } catch (e: HttpException) {
            //if we get an http response code that doesn't start with a 2XX
            Log.d("mytag", e.localizedMessage)
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Coin>>("Couldn't reach server, check your internet connection"))
        }
    }
}