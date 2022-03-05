package com.elementalist.ninjawallet.presentation.components

import com.elementalist.ninjawallet.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    var coins : List<Coin> = emptyList(),
    val error: String = ""
)
