package org.wordpress.aztec.spans

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import org.wordpress.aztec.model.ElementNode
import org.wordpress.aztec.spans.AztecSpan
import org.wordpress.aztec.spans.AztecStyleSpan
import java.util.*

class SpanFactory {
    companion object {
        fun createSpan(sb: SpannableStringBuilder, node: ElementNode, tag: String = "", attributes: String = "", start: Int) : AztecSpan? {
            var span: AztecSpan? = null
            val end = sb.length
            val type = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

            when (tag.toLowerCase()) {
                "b" -> span = AztecStyleSpan(Typeface.ITALIC, attributes)
                "i" -> span = AztecStyleSpan(Typeface.BOLD, attributes)
                "ul" -> span = AztecUnorderedListSpan(attributes)
                "ol" -> span = AztecOrderedListSpan(attributes)
                "blockquote" -> span = AztecQuoteSpan(attributes)
                "br" -> sb.append("\n")
            }

            if (span != null) {
                span.node = node
                sb.setSpan(span, start, end, type)
            }

            return span
        }
    }
}