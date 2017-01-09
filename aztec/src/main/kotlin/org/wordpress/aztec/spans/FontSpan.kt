package org.wordpress.aztec.spans

import android.text.TextPaint
import android.text.TextUtils
import android.text.style.CharacterStyle
import org.wordpress.aztec.model.ElementNode

class FontSpan : CharacterStyle, AztecContentSpan, AztecInlineSpan {

    private var TAG: String = "font"
    override var attributes: String
    override var node: ElementNode? = null

    @JvmOverloads
    constructor(attributes: String = "") : super() {
        this.attributes = attributes
    }

    override fun getStartTag(): String {
        if (TextUtils.isEmpty(attributes)) {
            return TAG
        }
        return TAG + attributes
    }

    override fun getEndTag(): String {
        return TAG
    }

    override fun updateDrawState(tp: TextPaint?) {
    }
}