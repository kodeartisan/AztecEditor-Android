package org.wordpress.aztec.spans

import android.text.TextUtils
import org.wordpress.aztec.model.ElementNode

class AztecListItemSpan : AztecSpan {

    private val TAG = "li"

    override var attributes: String
    override var node: ElementNode? = null

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
