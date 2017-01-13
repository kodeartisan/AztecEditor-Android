package org.wordpress.aztec.model

import org.wordpress.aztec.spans.AztecSpan
import java.util.*

class ListItemNode(initialStart: Int, attributes: String, parent: ElementNode?, children: ArrayList<Element> = ArrayList()) : ElementNode(initialStart, "li", attributes, parent, children) {
}