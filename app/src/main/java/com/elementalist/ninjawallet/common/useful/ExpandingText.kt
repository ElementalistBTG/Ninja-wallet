package com.elementalist.ninjawallet.common.useful

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.sp
import com.elementalist.ninjawallet.common.Constants.MINIMIZED_MAX_LINES

@Composable
fun ExpandingText(
    modifier: Modifier = Modifier,
    text: String,
    context: Context
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var textWithMoreLess by remember { mutableStateOf(text) }
    val textLayoutResult = textLayoutResultState.value

    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                textWithMoreLess = "$text (Show Less)"
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {//Returns true if either vertical overflow or horizontal overflow happens.
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val showMoreString = "... (Show More)"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                textWithMoreLess = "$adjustedText$showMoreString"

                isClickable = true
                //We basically need to assign this here so that the Text is only clickable if the state is not expanded,
                // but there is visual overflow. Otherwise, it means that the text given to the composable is not exceeding the max lines.
            }
        }
    }

    val annotatedStringLinks = remember {
        AnnotatedStringLinks(textWithMoreLess)
    }

    //Show different color on "Show More/Less"
    val annotatedStringFinal = buildAnnotatedString {
        if (isClickable) {
            append(
                annotatedStringLinks.substring(
                    startIndex = 0,
                    endIndex = annotatedStringLinks.length - 12
                )
            )
            pushStringAnnotation(tag = "show_more_tag", annotation = "")
            withStyle(SpanStyle(MaterialTheme.colors.primaryVariant)) {
                append(
                    annotatedStringLinks.substring(
                        startIndex = annotatedStringLinks.length - 11,
                        endIndex = annotatedStringLinks.length
                    )
                )
            }
            pop()
        } else {
            append(annotatedStringLinks)
        }
    }

    // UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

    SelectionContainer {
        ClickableText(
            text = annotatedStringFinal,
            style = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = 15.sp
            ),
            onClick = { offset ->
                annotatedStringFinal.getStringAnnotations(
                    tag = "link_tag",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
                if (isClickable) {
                    annotatedStringFinal.getStringAnnotations(
                        tag = "show_more_tag",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        isExpanded = !isExpanded
                    }
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
            onTextLayout = { textLayoutResultState.value = it },
            modifier = modifier
                .animateContentSize()
        )
    }

}




