package com.elementalist.ninjawallet.presentation.coin_detail.components

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
import com.elementalist.ninjawallet.common.Constants.curr1
import com.elementalist.ninjawallet.common.Constants.curr2
import com.elementalist.ninjawallet.common.Constants.curr3
import com.elementalist.ninjawallet.common.Constants.curr4
import com.elementalist.ninjawallet.common.Constants.curr5
import com.elementalist.ninjawallet.common.Constants.curr6
import com.elementalist.ninjawallet.common.Constants.priceChange1
import com.elementalist.ninjawallet.common.Constants.priceChange2
import com.elementalist.ninjawallet.common.Constants.priceChange3
import com.elementalist.ninjawallet.common.Constants.priceChange4
import com.elementalist.ninjawallet.common.Constants.priceChange5
import com.elementalist.ninjawallet.domain.model.Coin
import java.text.NumberFormat

const val coinWeight = 1f
const val priceWeight = 0.8f
const val pricePercentageWeight = 0.7f
const val marketCapWeight = 1.3f

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (Coin) -> Unit,
    pricePercentage: String
) {
    val price2decimals =
        if (coin.currentPrice > 1.0) "%.2f".format(coin.currentPrice).toDouble() else
            "%.4f".format(coin.currentPrice).toDouble()

    val pricePercentage2decimals = when (pricePercentage) {
        priceChange1 -> "%.2f".format(coin.priceChange1hr).toDouble()
        priceChange3 -> "%.2f".format(coin.priceChange7d).toDouble()
        priceChange4 -> "%.2f".format(coin.priceChange14d).toDouble()
        priceChange5 -> "%.2f".format(coin.priceChange30d).toDouble()
        priceChange2 -> "%.2f".format(coin.priceChange24hr).toDouble()
        else -> 0.0
    }

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
            modifier = Modifier.weight(coinWeight),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = price2decimals.toString(),
            modifier = Modifier.weight(priceWeight),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "$pricePercentage2decimals%",
            color = if (pricePercentage2decimals > 0.0) Color.Green else Color.Red,
            modifier = Modifier.weight(pricePercentageWeight),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        val marketCapFormatted = NumberFormat.getIntegerInstance().format(coin.marketCap)
        Text(
            text = marketCapFormatted,
            modifier = Modifier.weight(marketCapWeight),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HeadersLine(
    pricePercentageString: String,
    currency: String
) {
    val currencySymbol = when (currency) {
        curr1 -> "₿"
        curr2 -> "$"
        curr3 -> "€"
        curr4 -> "¥"
        curr5 -> "£"
        curr6 -> "CHF"
        else -> "$"
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Coin",
            modifier = Modifier.weight(coinWeight),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "Price (${currencySymbol})",
            modifier = Modifier.weight(priceWeight),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = pricePercentageString,
            modifier = Modifier.weight(pricePercentageWeight),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "Market Cap (${currencySymbol})",
            modifier = Modifier.weight(marketCapWeight),
            textAlign = TextAlign.Center
        )
    }

}