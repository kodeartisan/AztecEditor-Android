package org.wordpress.aztec.model

import android.text.SpannableStringBuilder

interface Element {
    var start: Int
    var end: Int
    var length: Int

    fun toHtml() : String
    fun toSpanned(sb: SpannableStringBuilder)
    fun onTextChanged(changeStart: Int, removeCount: Int, addedText: String)
}