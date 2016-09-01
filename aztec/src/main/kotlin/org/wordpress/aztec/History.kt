package org.wordpress.aztec

import android.text.Editable
import android.text.SpannableStringBuilder
import android.widget.EditText
import org.wordpress.aztec.source.SourceViewEditText
import java.util.*

class History(val historyEnabled: Boolean, val historySize: Int) {

    public val historyList = LinkedList<String>()

    private lateinit var inputBefore: String
    private lateinit var inputLast: String

    private var historyWorking = false
    private var historyCursor = 0

    fun beforeTextChanged(text: String) {
        if (historyEnabled && !historyWorking) {
            inputBefore = text
        }
    }

    fun handleHistory(editText: EditText) {
        if (!historyEnabled || historyWorking) {
            return
        }

        if (editText is AztecText) {
            inputLast = editText.toFormattedHtml()
        }
        else if (editText is SourceViewEditText) {
            inputLast = editText.text.toString()
        }

        if (inputLast == inputBefore) {
            return
        }

        if (historyList.size >= historySize) {
            historyList.removeAt(0)
        }

        while (historyCursor != historyList.size) {
            historyList.removeAt(historyCursor)
        }

        historyList.add(inputBefore)
        historyCursor = historyList.size
    }

    fun redo(editText: EditText) {
        if (!redoValid()) {
            return
        }

        historyWorking = true

        if (historyCursor >= historyList.size - 1) {
            historyCursor = historyList.size

            if (editText is AztecText) {
                editText.fromHtml(inputLast)
            }
            else if (editText is SourceViewEditText) {
                editText.displayStyledHtml(inputLast)
            }
        } else {
            historyCursor++
            setTextFromHistory(editText)
        }

        editText.setSelection(editText.editableText.length)
        historyWorking = false
    }

    fun undo(editText: EditText) {
        if (!undoValid()) {
            return
        }

        historyWorking = true
        historyCursor--

        setTextFromHistory(editText)

        editText.setSelection(editText.editableText.length)
        historyWorking = false
    }

    private fun setTextFromHistory(editText: EditText) {
        if (editText is AztecText) {
            editText.fromHtml(historyList[historyCursor])
        } else if (editText is SourceViewEditText) {
            editText.displayStyledHtml(historyList[historyCursor])
        }
    }

    fun redoValid(): Boolean {
        if (!historyEnabled || historySize <= 0 || historyList.size <= 0 || historyWorking) {
            return false
        }

        return historyCursor < historyList.size - 1 || historyCursor >= historyList.size - 1
    }

    fun undoValid(): Boolean {
        if (!historyEnabled || historySize <= 0 || historyWorking) {
            return false
        }

        if (historyList.size <= 0 || historyCursor <= 0) {
            return false
        }

        return true
    }

    fun clearHistory() {
        inputLast = ""
        historyList.clear()
    }
}
