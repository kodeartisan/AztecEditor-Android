package org.wordpress.aztec.toolbar

import org.wordpress.aztec.R
import org.wordpress.aztec.ElementType
import java.util.*

/**
 * Describes what actions toolbar can perform and what buttons they are bound to
 */
enum class ToolbarAction constructor(val buttonId: Int, val actionType: ToolbarActionType, val textFormat: ElementType?) {
    ADD_MEDIA(R.id.format_bar_button_media, ToolbarActionType.OTHER, null),
    HEADING(R.id.format_bar_button_heading, ToolbarActionType.OTHER, null),
    BOLD(R.id.format_bar_button_bold, ToolbarActionType.INLINE_STYLE, ElementType.BOLD),
    ITALIC(R.id.format_bar_button_italic, ToolbarActionType.INLINE_STYLE, ElementType.ITALIC),
    STRIKETHROUGH(R.id.format_bar_button_strikethrough, ToolbarActionType.INLINE_STYLE, ElementType.STRIKETHROUGH),
    UNORDERED_LIST(R.id.format_bar_button_ul, ToolbarActionType.BLOCK_STYLE, ElementType.UNORDERED_LIST),
    ORDERED_LIST(R.id.format_bar_button_ol, ToolbarActionType.BLOCK_STYLE, ElementType.ORDERED_LIST),
    QUOTE(R.id.format_bar_button_quote, ToolbarActionType.BLOCK_STYLE, ElementType.QUOTE),
    LINK(R.id.format_bar_button_link, ToolbarActionType.OTHER, ElementType.LINK),
    MORE(R.id.format_bar_button_more, ToolbarActionType.OTHER, ElementType.MORE),
    PAGE(R.id.format_bar_button_page, ToolbarActionType.OTHER, ElementType.PAGE),
    HTML(R.id.format_bar_button_html, ToolbarActionType.OTHER, null);

    companion object {
        fun getToolbarActionForStyle(style: ElementType): ToolbarAction? {
            ToolbarAction.values().forEach { if (it.textFormat != null && it.textFormat == style) return it }
            return null
        }

        fun getToolbarActionsForStyles(styles: ArrayList<ElementType>): ArrayList<ToolbarAction> {
            val actions = ArrayList<ToolbarAction>()
            styles.forEach {
                val action = getToolbarActionForStyle(it)
                if (action != null) {
                    actions.add(action)
                }
            }
            return actions
        }

    }

    fun isStylingAction(): Boolean {
        return actionType == ToolbarActionType.INLINE_STYLE || actionType == ToolbarActionType.BLOCK_STYLE
    }
}
