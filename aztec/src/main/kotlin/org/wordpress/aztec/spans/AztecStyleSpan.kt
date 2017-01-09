package org.wordpress.aztec.spans

import android.graphics.Typeface
import android.text.TextUtils
import android.text.style.StyleSpan
import org.wordpress.aztec.model.ElementNode

class AztecStyleSpan : StyleSpan, AztecContentSpan, AztecInlineSpan {

    var tag: String = ""
    override var attributes: String
    override var node: ElementNode? = null

    constructor(style: Int, attributes: String = "") : super(style) {
        this.attributes = attributes

        when (style) {
            Typeface.BOLD -> {
                tag = "b"
            }
            Typeface.ITALIC -> {
                tag = "i"
            }
        }
    }

    override fun getStartTag(): String {
        if (TextUtils.isEmpty(attributes)) {
            return tag
        }
        return tag + attributes
    }

    override fun getEndTag(): String {
        return tag
    }
}