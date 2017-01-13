package org.wordpress.aztec.model

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import org.wordpress.aztec.spans.AztecListItemSpan
import org.wordpress.aztec.spans.AztecStyleSpan
import org.wordpress.aztec.spans.AztecUnorderedListSpan
import org.wordpress.aztec.spans.SpanFactory
import java.util.*

abstract class ListNode(initialStart: Int, tag: String, attributes: String, parent: ElementNode?, children: ArrayList<Element>) : ElementNode(initialStart, tag, attributes, parent, children) {

    override fun onTextChanged(changeStart: Int, removeCount: Int, addedText: String) {
        super.onTextChanged(changeStart, removeCount, addedText)
    }

    override fun addNode(node: ElementNode) {
        children.add(children.lastIndex, node)
    }
}