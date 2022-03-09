package com.elementalist.ninjawallet.common.useful

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.elementalist.ninjawallet.common.Constants
import java.util.regex.Pattern

@Composable
fun ExpandingText2(
    modifier: Modifier = Modifier,
    text: String,
    context: Context
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var textWithMoreLess by remember { mutableStateOf(text) }
    val textLayoutResult = textLayoutResultState.value

//    LaunchedEffect(textLayoutResult) {
//        if (textLayoutResult == null) return@LaunchedEffect
//
//        when {
//            isExpanded -> {
//                textWithMoreLess = "$annotatedStringLinks (Show Less)"
//            }
//            !isExpanded && textLayoutResult.hasVisualOverflow -> {//Returns true if either vertical overflow or horizontal overflow happens.
//                val lastCharIndex = textLayoutResult.getLineEnd(Constants.MINIMIZED_MAX_LINES - 1)
//                val showMoreString = "... (Show More)"
//                val adjustedText = annotatedStringLinks
//                    .substring(startIndex = 0, endIndex = lastCharIndex)
//                    .dropLast(showMoreString.length)
//                    .dropLastWhile { it == ' ' || it == '.' }
//
//                textWithMoreLess = "$adjustedText$showMoreString"
//
//                isClickable = true
//                //We basically need to assign this here so that the Text is only clickable if the state is not expanded,
//                // but there is visual overflow. Otherwise, it means that the text given to the composable is not exceeding the max lines.
//            }
//        }
//    }

    val annotatedStringFinal = buildAnnotatedString {
        val HTML_A_TAG_PATTERN = Pattern.compile(
            "(?i)<a([^>]+)>(.+?)</a>",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )
        val HTML_A_HREF_TAG_PATTERN = Pattern.compile(
            "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )
        val charSpecial1 = Pattern.compile("[<]")
        val charSpecial2 = Pattern.compile("[>]")
        val matcher = HTML_A_TAG_PATTERN.matcher(text)
        var matchStart: Int = 0
        var matchEnd: Int = 0
        var previousMatchStart: Int = 0

        while (matcher.find()) {
            matchStart = matcher.start(1)
            matchEnd = matcher.end()
            val beforeMatch = text.substring(
                startIndex = previousMatchStart,
                endIndex = matchStart - 2
            )
            val tagMatch = text.substring(
                startIndex = text.indexOf(
                    char = '>',
                    startIndex = matchStart
                ) + 1,
                endIndex = text.indexOf(
                    char = '<',
                    startIndex = matchStart + 1
                ),
            )
            append(
                beforeMatch
            )
            // attach a string annotation that stores a URL to the text
            val annotation = text.substring(
                startIndex = matchStart + 7,
                endIndex = text.indexOf(
                    char ='"',
                    startIndex = matchStart + 7,
                ) - 1
            )
            Log.d("annotation",tagMatch)
            pushStringAnnotation(tag = "link_tag", annotation = annotation)
            withStyle(
                SpanStyle(
                    color = Color(0xff64B5F6),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(
                    tagMatch
                )
            }
            pop()
            previousMatchStart = matchEnd
        }
        if (text.length > matchEnd) {
            append(
                text.substring(
                    startIndex = matchEnd,
                    endIndex = text.length
                )
            )
        }
        if (isClickable) {
            pushStringAnnotation(tag = "show_more_tag", annotation = "")
            withStyle(SpanStyle(MaterialTheme.colors.primaryVariant)) {
                append(
                    "...Show More"
                )
            }
            pop()
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
            maxLines = if (!isExpanded) Int.MAX_VALUE else Constants.MINIMIZED_MAX_LINES,
            onTextLayout = { textLayoutResultState.value = it },
            modifier = modifier
                .animateContentSize()
        )
    }
}

