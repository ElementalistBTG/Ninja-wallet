package com.elementalist.ninjawallet.presentation.coin_detail.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.Links

@Composable
fun LinksListItem(
    link: Links,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        if (link.blockchain_site.isNotEmpty()) {
            Text(
                text = "Blockchain",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(4.dp))
            link.blockchain_site.forEach { site_link ->
                if(site_link.isNotBlank()){
                    Text(
                        text = site_link,
                        style = MaterialTheme.typography.body2,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (link.homepage.isNotEmpty()) {
            Text(
                text = "Website",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(4.dp))
            link.homepage.forEach { site_link ->
                Log.d("mytag", site_link.toString())
                Text(
                    text = site_link,
                    style = MaterialTheme.typography.body2,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}