package com.plcoding.cryptocurrencyappyt.presentation.coin_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import java.text.NumberFormat

val coinWeight = 1f
val priceWeight = 0.8f
val pricePercentageWeight = 0.7f
val marketCapWeight = 1.3f

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (Coin) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin) }
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(coinWeight)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        val price2decimals =
            if (coin.currentPrice > 1.0) "%.2f".format(coin.currentPrice).toDouble() else
                "%.4f".format(coin.currentPrice).toDouble()
        Text(
            text = price2decimals.toString(),
            modifier = Modifier.weight(priceWeight)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        val pricePercentage2decimals = "%.2f".format(coin.priceChange24hr).toDouble()
        Text(
            text = "$pricePercentage2decimals%",
            color = if(pricePercentage2decimals>0.0) Color.Green else Color.Red,
            modifier = Modifier.weight(pricePercentageWeight)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        val marketCapFormatted = NumberFormat.getIntegerInstance().format(coin.marketCap)
        Text(
            text = marketCapFormatted,
            modifier = Modifier.weight(marketCapWeight),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun HeadersLine() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Coin",
            modifier = Modifier.weight(coinWeight)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "Price ($)",
            modifier = Modifier.weight(priceWeight)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "24Hr",
            modifier = Modifier.weight(pricePercentageWeight)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "Market Cap ($)",
            modifier = Modifier.weight(marketCapWeight),
            textAlign = TextAlign.End
        )
    }

}