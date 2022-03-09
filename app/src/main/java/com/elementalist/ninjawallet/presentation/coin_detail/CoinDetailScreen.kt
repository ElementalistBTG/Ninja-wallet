package com.elementalist.ninjawallet.presentation.coin_detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elementalist.ninjawallet.common.useful.ExpandingText
import com.elementalist.ninjawallet.common.useful.ExpandingText2
import com.elementalist.ninjawallet.data.local.entity.CoinsEntity
import com.elementalist.ninjawallet.presentation.coin_detail.components.LinksListItem

@Composable
fun CoinDetailScreen(
//nav controller is not needed since we don't navigate to anywhere through this screen
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val coinExistsInWatchlist = viewModel.exists.observeAsState()
    val context = LocalContext.current
    val addCoinToWatchlist = {
        state.coin?.let {
            CoinsEntity(
                id = it.id
            )
        }?.let {
            viewModel.addCoinToWatchlist(coin = it)
            Toast.makeText(
                context,
                "Coin added to WatchList",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    val removeCoinFromWatchlist = {
        state.coin?.let {
            CoinsEntity(
                id = it.id
            )
        }?.let {
            viewModel.removeCoinFromWatchlist(coin = it)
            Toast.makeText(
                context,
                "Coin removed from WatchList",
                Toast.LENGTH_SHORT
            ).show()
        }
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
                        horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "${coin.market_cap_rank}. ${coin.name} (${coin.symbol})",
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.weight(0.8f).align(Alignment.CenterVertically)
                        )
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(viewModel.state.value.coin?.image?.large)
                                .crossfade(true)
                                .build() ,
                            contentDescription = null,
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.clip(CircleShape).weight(0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    ExpandingText2(
                        text = coin.description.en,
                        context = context)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Links",
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    LinksListItem(
                        link = coin.links,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    if (coinExistsInWatchlist.value != true) {
                        ExtendedFloatingActionButton(
                            text = { Text(text = "Add to Watchlist") },
                            onClick = {
                                addCoinToWatchlist()
                            },
                            icon = { Icon(Icons.Filled.Favorite, "Add to Watchlist") }
                        )
                    } else {
                        ExtendedFloatingActionButton(
                            text = { Text(text = "Remove from Watchlist") },
                            onClick = { removeCoinFromWatchlist() },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Remove from Watchlist"
                                )
                            },
                            backgroundColor = Color.DarkGray
                        )
                    }

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