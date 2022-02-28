package com.elementalist.ninjawallet.presentation.coin_detail

import com.elementalist.ninjawallet.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin : CoinDetail? = null,
    val error: String = ""
)
