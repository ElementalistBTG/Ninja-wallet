package com.elementalist.ninjawallet.presentation.settings


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elementalist.ninjawallet.common.Constants.priceChange1
import com.elementalist.ninjawallet.common.Constants.priceChange2
import com.elementalist.ninjawallet.common.Constants.priceChange3
import com.elementalist.ninjawallet.common.Constants.priceChange4
import com.elementalist.ninjawallet.common.Constants.priceChange5


@Composable
fun PriceChangePercentageRadioGroup(viewModel: SettingsViewModel) {
    val radioList = listOf(priceChange1, priceChange2, priceChange3, priceChange4, priceChange5)
    val selected by viewModel.percentageSelected.observeAsState()

    @Composable
    fun radioGroup(title: String) {
        Column {
            Text(
                text = title,
                modifier = Modifier
                    .clickable(onClick = { viewModel.percentageChange(title) })
            )
            RadioButton(
                selected = selected == title,
                onClick = { viewModel.percentageChange(title) })
        }
        Spacer(modifier = Modifier.size(4.dp))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(
                unbounded = false
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (radioItem in radioList) {
            radioGroup(title = radioItem)
        }
    }


}