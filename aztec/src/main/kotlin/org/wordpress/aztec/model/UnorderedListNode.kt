package org.wordpress.aztec.model

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import org.wordpress.aztec.spans.AztecListItemSpan
import org.wordpress.aztec.spans.AztecStyleSpan
import org.wordpress.aztec.spans.AztecUnorderedListSpan
//import java.util.*
//
//class UnorderedListNode(attributes: String, children: ArrayList<Element>, parent: ElementNode?) : ElementNode("ul", attributes, children, parent) {
//    override fun toSpanned(sb: SpannableStringBuilder) {
//        val start = sb.length
//        children.forEach {
//            it.toSpanned(sb)
//        }
//        val span = AztecUnorderedListSpan(attributes)
//        sb.setSpan(span, start, sb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//    }
//}