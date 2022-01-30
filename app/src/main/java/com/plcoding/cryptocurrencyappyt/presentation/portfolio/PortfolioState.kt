package com.plcoding.cryptocurrencyappyt.presentation.portfolio

import com.plcoding.cryptocurrencyappyt.domain.model.Coin

data class PortfolioState(
    val isLoading: Boolean = false,
    val coins : List<Coin> = emptyList(),
    val error: String = ""
)
