package com.miwfem.socialtourismnetwork.presentation.common

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*


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

fun String.parseLongDate(): Date? {
    val format = SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    return format.parse(this)
}

@SuppressLint("SimpleDateFormat")
fun String.parseShortDate(): Date? {
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    return format.parse(this)
}

fun stripAccents(s: String): String {
    var s = s
    s = Normalizer.normalize(s, Normalizer.Form.NFD)
    s = s.replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
    return s
}

fun String.simplifyString(): String {
    return stripAccents(this.replace(" ", "").toLowerCase(Locale.ROOT))
}