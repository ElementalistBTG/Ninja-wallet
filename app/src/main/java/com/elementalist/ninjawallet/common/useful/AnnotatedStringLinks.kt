package com.elementalist.ninjawallet.common.useful

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import java.util.regex.Pattern

fun AnnotatedStringLinks(textWithMoreLess: String): AnnotatedString = buildAnnotatedString {
    val HTML_A_TAG_PATTERN = Pattern.compile(
        "(?i)<a([^>]+)>(.+?)</a>",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
    )
    val HTML_A_HREF_TAG_PATTERN = Pattern.compile(
        "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
    )
    val matcher = HTML_A_TAG_PATTERN.matcher(textWithMoreLess)
    var matchStart: Int
    var matchEnd: Int
    var previousMatchStart: Int = 0
    try {
        while (matcher.find()) {
            matchStart = matcher.start(1)
            matchEnd = matcher.end()
            val beforeMatch = textWithMoreLess.substring(
                startIndex = previousMatchStart,
                endIndex = matchStart -2
            )
            val linkMatch = textWithMoreLess.substring(
                startIndex = matchStart -2,
                endIndex = matchEnd
            )
            append(
                beforeMatch
            )
            // attach a string annotation that stores a URL to the text
            pushStringAnnotation(tag = "link_tag", annotation = "https://www.google.com")
            withStyle(SpanStyle(Color.Blue)) {
                append(
                    linkMatch
                )
            }
            pop()
            previousMatchStart = matchEnd
        }
    } catch (e: Exception) {
        Log.d("mytag", "Exception: $e")
    }
}
