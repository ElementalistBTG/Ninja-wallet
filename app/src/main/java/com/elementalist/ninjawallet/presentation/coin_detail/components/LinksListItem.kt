package com.elementalist.ninjawallet.presentation.coin_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.elementalist.ninjawallet.common.useful.LinksContainer
import com.elementalist.ninjawallet.common.useful.textWithLinks
import com.elementalist.ninjawallet.data.remote.dto.CoinDetails.Links

@Composable
fun LinksListItem(
    link: Links,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
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
                if (site_link.isNotBlank()) {
                    val annotatedString = textWithLinks(site_link, tag = "link_tag")
                    LinksContainer(
                        annotatedString = annotatedString,
                        uriHandler = uriHandler,
                        tag = "link_tag"
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
                val annotatedString = textWithLinks(site_link, tag = "link_tag")
                LinksContainer(
                    annotatedString = annotatedString,
                    uriHandler = uriHandler,
                    tag = "link_tag"
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}