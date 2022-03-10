package com.elementalist.ninjawallet.common.useful

import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle

@Composable
fun LinksContainer (annotatedString: AnnotatedString, uriHandler: UriHandler,tag : String ){
    SelectionContainer {
        ClickableText(
            text = annotatedString,
            style = TextStyle(
                fontStyle = FontStyle.Italic
            ),
            onClick = { offset ->
                annotatedString.getStringAnnotations(
                    tag = tag,
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
            }
        )
    }
}