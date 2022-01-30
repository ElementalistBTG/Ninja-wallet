package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    //overwrite the invoke/execute fun -> So we can use the use case as it was a function
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try{
            //we first emit a loading status
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            //if the above line is successful we can emit the Resourse.Success to our viewmodel
            emit(Resource.Success<CoinDetail>(coin))
        }catch (e:HttpException){
            //if we get an http response code that doesn't start with a 2XX
            emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occurred!"))
        }catch (e:IOException){
            //if our repository api can't talk to the remote api
            //e.g. if we have no internet connection
            emit(Resource.Error<CoinDetail>("Couldn't reach server, check your internet connection"))
        }
    }
}