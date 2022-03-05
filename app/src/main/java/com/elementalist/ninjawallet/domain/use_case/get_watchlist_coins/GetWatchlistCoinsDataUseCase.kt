package com.elementalist.ninjawallet.domain.use_case.get_watchlist_coins


import android.util.Log
import com.elementalist.ninjawallet.common.Resource
import com.elementalist.ninjawallet.data.remote.dto.CoinsListMC.toCoins
import com.elementalist.ninjawallet.domain.model.Coin
import com.elementalist.ninjawallet.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWatchlistCoinsDataUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    //overwrite the invoke/execute fun -> So we can use the use case as it was a function
    operator fun invoke(
        currency: String,
        per_page: Int,
        page: Int
    ): Flow<Resource<List<Coin>>> = flow {
        try {
            //we first emit a loading status
            emit(Resource.Loading<List<Coin>>())

            val coinEntities = repository.getWatchlistCoinsFromDB()
            var coinIds = ""
            if(coinEntities.isNotEmpty()){
                coinIds = coinEntities.first().id
                for (coinEntity in coinEntities) {
                    if(coinEntity!=coinEntities.first()){
                        coinIds += ",${coinEntity.id}"
                    }
                }
                val watchlistItems = repository.getWatchlistCoinData(
                    ids = coinIds,
                    currency = currency,
                    per_page = per_page,
                    page = page
                ).map { it.toCoins() }

                //if the above line is successful we can emit the Resource.Success to our viewmodel
                emit(Resource.Success<List<Coin>>(watchlistItems))
            }else{
                emit(Resource.Success<List<Coin>>(listOf<Coin>()))
            }

        } catch (e: HttpException) {
            //if we get an http response code that doesn't start with a 2XX
            Log.d("mytag", e.localizedMessage)
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Coin>>("Couldn't reach server, check your internet connection"))
        }
    }
}