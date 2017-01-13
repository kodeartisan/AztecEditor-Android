/*
 * Copyright (C) 2016 Automattic
 * Copyright (C) 2015 Matthew Lee
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wordpress.aztec

import android.content.Context
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.CharacterStyle
import android.text.style.ImageSpan
import android.text.style.ParagraphStyle
import org.ccil.cowan.tagsoup.Parser
import org.wordpress.aztec.model.*
import org.wordpress.aztec.spans.*
import org.xml.sax.*
import org.xml.sax.ext.LexicalHandler
import java.io.IOException
import java.io.StringReader
import java.util.*

class HtmlModelParser {

    fun fromHtml(source: String): ElementNode {
        val tidySource = tidy(source)

        val parser = Parser()
        try {
            parser.setProperty(Parser.schemaProperty, Html.HtmlParser.schema)
            parser.setFeature(Parser.rootBogonsFeature, false) //allows the unknown tags to exist without root element
        } catch (e: org.xml.sax.SAXNotRecognizedException) {
            // Should not happen.
            throw RuntimeException(e)
        } catch (e: org.xml.sax.SAXNotSupportedException) {
            // Should not happen.
            throw RuntimeException(e)
        }

        return HtmlToModelConverter(source, parser).convert()
    }

    fun toHtml(root: ElementNode): String {
        return root.toHtml()
    }

    internal class HtmlToModelConverter(private val source: String, parser: Parser) : ContentHandler, LexicalHandler {
        private var root: ElementNode
        private val reader: XMLReader = parser

        init {
            root = ElementNode()
        }

        fun convert(): ElementNode {
            reader.contentHandler = this
            try {
                reader.setProperty(Parser.lexicalHandlerProperty, this)
                reader.parse(InputSource(StringReader(source)))
            } catch (e: IOException) {
                // We are reading from a string. There should not be IO problems.
                throw RuntimeException(e)
            } catch (e: SAXException) {
                // TagSoup doesn't throw parse exceptions.
                throw RuntimeException(e)
            }

            return root
        }

        private fun handleStartTag(tag: String, attributes: Attributes) {

            if (!tag.equals("aztec_cursor", ignoreCase = true)) {
                startElement(tag, attributes)
            }

            //        if (mUnknownTagLevel != 0) {
            //            if (tag.equalsIgnoreCase("aztec_cursor")) {
            //                handleCursor(mSpannableStringBuilder);
            //                return;
            //            }
            //            // Swallow opening tag and attributes in current Unknown element
            //            mUnknown.rawHtml.append('<').append(tag).append(Html.stringifyAttributes(attributes)).append('>');
            //            mUnknownTagLevel += 1;
            //            return;
            //        }
        }

        private fun startElement(tag: String, attributes: Attributes) {
            if (!tag.equals("aztec_cursor", ignoreCase = true) && !tag.equals("body", ignoreCase = true) && !tag.equals("html", ignoreCase = true)) {

                val newElement: ElementNode
                when (tag) {
                    "ul" -> newElement = UnorderedListNode(root.end ?: 0, Html.stringifyAttributes(attributes).toString(), root)
                    "ol" -> newElement = OrderedListNode(root.end ?: 0, Html.stringifyAttributes(attributes).toString(), root)
                    "li" -> newElement = ListItemNode(root.end ?: 0, Html.stringifyAttributes(attributes).toString(), root)
                    else -> {
                        newElement = ElementNode(root.end ?: 0, tag, Html.stringifyAttributes(attributes).toString(), root)
                    }
                }
                root.addNode(newElement)
                root = newElement
            }
        }

        private fun endElement() {
            if (root.parent != null) {
                root = root.parent!!
            }
        }

        private fun handleEndTag(tag: String) {

            if (!tag.equals("aztec_cursor", ignoreCase = true)) {
                endElement()
            }
        }


        override fun setDocumentLocator(locator: Locator) {}

        @Throws(SAXException::class)
        override fun startDocument() {
        }

        @Throws(SAXException::class)
        override fun endDocument() {
        }

        @Throws(SAXException::class)
        override fun startPrefixMapping(prefix: String, uri: String) {
        }

        @Throws(SAXException::class)
        override fun endPrefixMapping(prefix: String) {
        }

        @Throws(SAXException::class)
        override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
            handleStartTag(localName, attributes)
        }

        @Throws(SAXException::class)
        override fun endElement(uri: String, localName: String, qName: String) {
            handleEndTag(localName)
        }

        @Throws(SAXException::class)
        override fun characters(ch: CharArray, start: Int, length: Int) {
            // If unknown tag, then swallow everything

            val sb = StringBuilder()

            /*
         * Ignore whitespace that immediately follows other whitespace;
         * newlines count as spaces.
         */

            for (i in 0..length - 1) {
                val c = ch[i + start]

                if (c == ' ' || c == '\n') {
                    val pred: Char
                    val len = sb.length

                    if (len == 0) {
                        pred = '\n'
                    } else {
                        pred = sb[len - 1]
                    }

                    if (pred != ' ' && pred != '\n') {
                        sb.append(' ')
                    }
                } else {
                    sb.append(c)
                }
            }

            root?.addText(sb.toString())
        }

        @Throws(SAXException::class)
        override fun ignorableWhitespace(ch: CharArray, start: Int, length: Int) {
        }

        @Throws(SAXException::class)
        override fun processingInstruction(target: String, data: String) {
        }

        @Throws(SAXException::class)
        override fun skippedEntity(name: String) {
        }

        @Throws(SAXException::class)
        override fun startDTD(s: String, s1: String, s2: String) {

        }

        @Throws(SAXException::class)
        override fun endDTD() {

        }

        @Throws(SAXException::class)
        override fun startEntity(s: String) {

        }

        @Throws(SAXException::class)
        override fun endEntity(s: String) {

        }

        @Throws(SAXException::class)
        override fun startCDATA() {

        }

        @Throws(SAXException::class)
        override fun endCDATA() {

        }

        @Throws(SAXException::class)
        override fun comment(chars: CharArray, start: Int, length: Int) {
            //        if (mUnknownTagLevel != 0) {
            //            mUnknown.rawHtml.append("<!--");
            //            for (int i = 0; i < length; i++) {
            //                mUnknown.rawHtml.append(chars[i + start]);
            //            }
            //            mUnknown.rawHtml.append("-->");
            //            return;
            //        }

            val comment = String(chars, start, length)
        }
    }

    private fun tidy(html: String): String {
        return html
                .replace("&#8203;", "")
                .replace("&#65279;", "")
                .replace("(</? ?br>)*</blockquote>".toRegex(), "</blockquote>")
                .replace("(</? ?br>)*</p>".toRegex(), "</p>")
                .replace("(</? ?br>)*</li>".toRegex(), "</li>")
    }
}
