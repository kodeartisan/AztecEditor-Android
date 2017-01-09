package org.wordpress.aztec.spans

import android.text.TextUtils
import android.text.style.SubscriptSpan
import org.wordpress.aztec.model.ElementNode

class AztecSubscriptSpan : SubscriptSpan, AztecContentSpan, AztecInlineSpan {

    private var TAG: String = "sub"
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
}