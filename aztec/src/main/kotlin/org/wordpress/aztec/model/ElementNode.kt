package org.wordpress.aztec.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.wordpress.aztec.spans.AztecSpan
import org.wordpress.aztec.spans.SpanFactory
import java.util.*


open class ElementNode @JvmOverloads constructor(private val initialStart: Int = 0,
                                                 val tag: String = "",
                                                 val attributes: String = "",
                                                 protected val children: ArrayList<Element> = ArrayList(),
                                                 val parent: ElementNode? = null,
                                                 var span: AztecSpan? = null) : Element {

    override var start: Int =  if (children.firstOrNull() != null) children.first().start else initialStart
    override var end: Int = if (children.lastOrNull() != null) children.last().end else initialStart

    public constructor(initialStart: Int = 0, tag: String = "", attributes: String = "", parent: ElementNode? = null) : this(initialStart, tag, attributes, ArrayList(), parent, null)

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

        span = SpanFactory.createSpan(sb, this, tag, attributes, start)
    }

    fun addText(text: String) {
        children.add(ElementText(end, text))
    }

    fun addNode(node: ElementNode) {
        children.add(node)
    }

    override fun onTextChanged(start: Int, removedCount: Int, addedText: String) {
        if (removedCount > 0) {
            children.removeIf { it.start >= start && start + removedCount >= it.end }
            children.filter { it.start < start && it.end > start ||  it.start < start + removedCount && it.end > start + removedCount}
                    .forEach { it.onTextChanged(start, removedCount, addedText) }
        }
    }
}