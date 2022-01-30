package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins_MC

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

class GetCoinsMCUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    //overwrite the invoke/execute fun -> So we can use the use case as it was a function
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            //we first emit a loading status
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoinsByMC().map{ it.toCoins() }//we want to return List<Coin> and not List<CoinDto>
            //if the above line is successful we can emit the Resourse.Success to our viewmodel
            emit(Resource.Success<List<Coin>>(coins))
        } catch (e: HttpException) {
            //if we get an http response code that doesn't start with a 2XX
            Log.d("mytag", e.localizedMessage)
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            //if our repository api can't talk to the remote api
            //e.g. if we have no internet connection
            emit(Resource.Error<List<Coin>>("Couldn't reach server, check your internet connection"))
        }
    }
}