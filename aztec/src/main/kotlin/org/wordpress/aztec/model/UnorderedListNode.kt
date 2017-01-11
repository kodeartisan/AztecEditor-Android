package org.wordpress.aztec.model

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import org.wordpress.aztec.spans.AztecListItemSpan
import org.wordpress.aztec.spans.AztecStyleSpan
import org.wordpress.aztec.spans.AztecUnorderedListSpan
import org.wordpress.aztec.spans.SpanFactory
import java.util.*

class UnorderedListNode(initialStart: Int, attributes: String, children: ArrayList<Element>, parent: ElementNode?) : ElementNode(initialStart, "ul", attributes, children, parent) {

    init {
        children.add(0, ElementText(start, "\n"))
        children.add(ElementText(end, "\n"))
    }

    override fun onTextChanged(start: Int, removedCount: Int, addedText: String) {
        super.onTextChanged(start, removedCount, addedText)
    }
}