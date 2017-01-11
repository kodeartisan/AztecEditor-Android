package org.wordpress.aztec.model

import android.text.SpannableStringBuilder

class ElementText(override var start: Int, var text: String = "") : Element {

    override var end: Int = start + text.length

    override fun toSpanned(sb: SpannableStringBuilder) {
        sb.append(text)
    }

    override fun toHtml(): String {
        return text
    }

    override fun onTextChanged(start: Int, removedCount: Int, addedText: String) {
        if (removedCount > 0) {
            text.removeRange(start, Math.min(start + removedCount, end))
        }
        text +=addedText
    }
}