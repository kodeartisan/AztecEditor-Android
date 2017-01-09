package org.wordpress.aztec.model

import android.text.SpannableStringBuilder

interface Element {
    fun toHtml() : String
    fun toSpanned(sb: SpannableStringBuilder)
}