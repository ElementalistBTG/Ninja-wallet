package com.plcoding.cryptocurrencyappyt.presentation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import com.plcoding.cryptocurrencyappyt.R


sealed class Screen(val route: String, val icon: Int? = null){
    object CoinListScreen: Screen("coin_list_screen", icon = R.drawable.baseline_list)
    object CoinDetailScreen: Screen("coin_detail_screen")
    //bottom bar
    object PortfolioScreen: Screen("portfolio_screen", icon = R.drawable.baseline_portfolio)
    //top bar settings
    object SettingsScreen: Screen("settings_screen")
}

