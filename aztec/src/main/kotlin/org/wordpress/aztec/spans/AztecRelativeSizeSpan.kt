package org.wordpress.aztec.spans

import android.text.TextUtils
import android.text.style.RelativeSizeSpan
import org.wordpress.aztec.model.ElementNode

class AztecRelativeSizeSpan : RelativeSizeSpan, AztecContentSpan, AztecInlineSpan {

    var tag: String = ""
    override var attributes: String
    override var node: ElementNode? = null

    @JvmOverloads
    constructor(tag: String, size: Float, attributes: String = "") : super(size) {
        this.tag = tag
        this.attributes = attributes
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