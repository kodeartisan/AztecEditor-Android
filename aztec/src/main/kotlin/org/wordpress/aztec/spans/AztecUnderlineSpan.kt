package org.wordpress.aztec.spans

import android.text.TextUtils
import android.text.style.UnderlineSpan
import org.wordpress.aztec.model.ElementNode

class AztecUnderlineSpan(override var attributes: String = "") : UnderlineSpan(), AztecContentSpan, AztecInlineSpan {

    private val TAG = "u"
    override var node: ElementNode? = null

    override fun getStartTag(): String {
        if (TextUtils.isEmpty(attributes)) {
            return TAG
        }
        return TAG + attributes
    }

    override fun getEndTag(): String {
        return TAG
    }
}