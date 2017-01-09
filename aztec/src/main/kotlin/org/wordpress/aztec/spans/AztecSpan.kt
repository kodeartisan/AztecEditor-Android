package org.wordpress.aztec.spans

import org.wordpress.aztec.model.ElementNode

interface AztecSpan {

    var attributes: String
    var node: ElementNode?

    fun getStartTag(): String
    fun getEndTag(): String

}
