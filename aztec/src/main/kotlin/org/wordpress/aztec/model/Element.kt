package org.wordpress.aztec.model

import android.text.SpannableStringBuilder

interface Element {
    var start: Int
    var end: Int

    fun toHtml() : String
    fun toSpanned(sb: SpannableStringBuilder)
    fun onTextChanged(start: Int, removedCount: Int, addedText: String)
}