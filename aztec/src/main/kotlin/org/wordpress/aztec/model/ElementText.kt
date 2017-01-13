package org.wordpress.aztec.model

import android.text.SpannableStringBuilder

class ElementText(override var start: Int, var text: String = "") : Element {

    override var length: Int = text.length
        get() = text.length
    override var end: Int = start + length
        get() = start + length

    override fun toSpanned(sb: SpannableStringBuilder) {
        sb.append(text)
    }

    override fun toHtml(): String {
        return text
    }

    override fun onTextChanged(changeStart: Int, removeCount: Int, addedText: String) {
        if (removeCount > 0) {
            text.removeRange(Math.max(start, changeStart), Math.min(changeStart + removeCount, end))
        }
        text += addedText
    }
}