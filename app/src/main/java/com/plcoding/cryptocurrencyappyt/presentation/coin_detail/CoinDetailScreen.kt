package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.cryptocurrencyappyt.common.useful.ExpandingText
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity
import com.plcoding.cryptocurrencyappyt.presentation.coin_detail.components.LinksListItem

@Composable
fun CoinDetailScreen(
//nav controller is not needed since we don't navigate to anywhere through this screen
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    fun addCoinToPortfolio() {
        state.coin?.let {
            CoinsEntity(
                id = it.id,
                name = it.name,
                symbol = it.symbol
            )
        }?.let { viewModel.addCoinToPorfolio(coin = it) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        state.coin?.let { coin ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${coin.market_cap_rank}. ${coin.name} (${coin.symbol})",
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.weight(8f)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    ExpandingText(text = coin.description.en)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Links",
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    LinksListItem(
                        link = coin.links,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    ExtendedFloatingActionButton(
                        text = { Text(text = "Add to Portfolio") },
                        onClick = { addCoinToPortfolio() },
                        icon = { Icon(Icons.Filled.Favorite, "Portfolio Coin") }
                    )
                }
            }
        }
        if (state.error.isNotBlank()) {//if it contains an error
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}