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
import com.elementalist.ninjawallet.common.Constants.curr1
import com.elementalist.ninjawallet.common.Constants.curr2
import com.elementalist.ninjawallet.common.Constants.curr3
import com.elementalist.ninjawallet.common.Constants.curr4
import com.elementalist.ninjawallet.common.Constants.curr5
import com.elementalist.ninjawallet.common.Constants.curr6

@Composable
fun CurrenciesRadioGroup(viewModel: SettingsViewModel) {
    val radioList = listOf(
        curr1,
        curr2,
        curr3,
        curr4,
        curr5,
        curr6
    )
    val selectedCurrency by viewModel.currencySelected.observeAsState()

    @Composable
    fun radioGroup(title: String) {
        Column {
            Text(
                text = title,
                modifier = Modifier
                    .clickable(onClick = { viewModel.currencyChange(title) })
            )
            RadioButton(
                selected = selectedCurrency == title,
                onClick = { viewModel.currencyChange(title) })
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