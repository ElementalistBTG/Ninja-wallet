package com.elementalist.ninjawallet.common.useful

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.elementalist.ninjawallet.ui.theme.linkColor

fun textWithLinks(site_link: String, tag : String): AnnotatedString {
    return buildAnnotatedString {
        pushStringAnnotation(tag = tag, annotation = site_link)
        withStyle(
            SpanStyle(
                color = linkColor,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(
                site_link
            )
        }
        pop()
    }
}
