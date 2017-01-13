package org.wordpress.aztec.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.wordpress.aztec.spans.AztecSpan
import org.wordpress.aztec.spans.SpanFactory
import java.util.*


open class ElementNode @JvmOverloads constructor(private val initialStart: Int = 0,
                                                 val tag: String = "",
                                                 val attributes: String = "",
                                                 val parent: ElementNode? = null,
                                                 protected val children: ArrayList<Element> = ArrayList(),
                                                 var span: AztecSpan? = null) : Element {

    override var length: Int = children.sumBy { it.length }
        get() = children.sumBy { it.length }

    override var start: Int = initialStart
        get() = if (children.firstOrNull() != null) children.first().start else initialStart

    override var end: Int = initialStart
        get() = start + length

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
        children.forEach {
            it.toSpanned(sb)
        }

        span = SpanFactory.createSpan(sb, this, tag, attributes)
    }

    fun addText(text: String) {
        children.add(ElementText(end, text))
    }

    open fun addNode(node: ElementNode) {
        children.add(node)
    }

    override fun onTextChanged(changeStart: Int, removeCount: Int, addedText: String) {
        if (removeCount > 0) {
            children.removeIf { it.start >= changeStart && changeStart + removeCount >= it.end }
            children.filter { it.start < changeStart && it.end > changeStart ||  it.start < changeStart + removeCount && it.end > changeStart + removeCount }
                    .forEach { it.onTextChanged(changeStart, removeCount, addedText) }
        }
    }
}