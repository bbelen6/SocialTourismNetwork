package com.miwfem.socialtourismnetwork.presentation.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan


fun String.setBoldText(wordsToAddSpan: List<String>): Spannable {
    val wordToSpan: Spannable = SpannableString(this)
    wordsToAddSpan.forEach { spanWord ->
        if (this.indexOf(spanWord) != -1)
            wordToSpan.setSpan(
                StyleSpan(Typeface.BOLD),
                this.indexOf(spanWord),
                spanWord.length + indexOf(spanWord),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
    }
    return wordToSpan
}