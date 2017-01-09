package org.wordpress.aztec.model

import android.text.SpannableStringBuilder

class ElementText(val text: String = "") : Element {
    override fun toSpanned(sb: SpannableStringBuilder) {
        sb.append(text)
    }

    override fun toHtml(): String {
        return text
    }
}