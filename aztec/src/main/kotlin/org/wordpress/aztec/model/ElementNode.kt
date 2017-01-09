package org.wordpress.aztec.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.wordpress.aztec.spans.AztecSpan
import org.wordpress.aztec.spans.SpanFactory
import java.util.*


open class ElementNode @JvmOverloads constructor(val tag: String = "",
                                                 val attributes: String = "",
                                                 val children: ArrayList<Element> = ArrayList(),
                                                 val parent: ElementNode? = null,
                                                 var span: AztecSpan?) : Element {

    public constructor(tag: String = "", attributes: String = "", parent: ElementNode? = null) : this(tag, attributes, ArrayList(), parent, null)

    override fun toHtml() : String {
        val sb = StringBuilder()
        sb.append("<")
        sb.append(tag)
        sb.append(attributes)

        if (children.isNotEmpty()) {
            sb.append(">")
            children.forEach {
                sb.append(it.toHtml())
            }
            sb.append("</")
            sb.append(tag)
            sb.append(">")
        } else {
            sb.append(" />")
        }

        return sb.toString()
    }

    override fun toSpanned(sb: SpannableStringBuilder) {
        val start = sb.length
        children.forEach {
            it.toSpanned(sb)
        }

        span = SpanFactory.createSpan(sb, this, tag, attributes, start)
    }
}