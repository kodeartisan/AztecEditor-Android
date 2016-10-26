/*
 * Copyright (C) 2016 Automattic
 * Copyright (C) 2015 Matthew Lee
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

package org.wordpress.aztec.spans

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.text.Layout
import android.text.Spanned
import android.text.TextUtils
import android.text.style.*
import android.util.TypedValue
import org.wordpress.aztec.R


class AztecQuoteSpan : AztecBlockSpan, ParagraphStyle, LineHeightSpan, LeadingMarginSpan {
    override fun getLeadingMargin(first: Boolean): Int {
        return 0
    }

    override fun drawLeadingMargin(c: Canvas?, p: Paint?, x: Int, dir: Int, top: Int, baseline: Int, bottom: Int, text: CharSequence?, start: Int, end: Int, first: Boolean, layout: Layout?) {
    }

    val rect = Rect()

    private val TAG: String = "blockquote"

    private var quoteBackground: Int = 0
    private var quoteColor: Int = 0
    private var quoteMargin: Int = 0
    private var quotePadding: Int = 0
    private var quoteWidth: Int = 0

    override var attributes: String? = null


    constructor(context: Context, attributes: String? = null) : super() {
        this.attributes = attributes
    }

    constructor(context: Context, quoteBackground: Int, quoteColor: Int, quoteMargin: Int, quoteWidth: Int, quotePadding: Int, attributes: String? = null) : this(context, attributes) {
        this.quoteBackground = quoteBackground
        this.quoteColor = quoteColor
        this.quoteMargin = quoteMargin
        this.quoteWidth = quoteWidth
        this.quotePadding = quotePadding
    }

//    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
//        return quotePadding + paint.measureText(text.subSequence(start, end).toString()).toInt() + quotePadding
//    }

    override fun getStartTag(): String {
        if (TextUtils.isEmpty(attributes)) {
            return TAG
        }
        return TAG + attributes
    }

    override fun getEndTag(): String {
        return TAG
    }

    override fun chooseHeight(text: CharSequence, start: Int, end: Int,
                              istartv: Int, v: Int,
                              fm: Paint.FontMetricsInt) {
        if (end == (text as Spanned).getSpanEnd(this)) {
            val ht = 200

            var need = ht - (v + fm.descent - fm.ascent - istartv)
            if (need > 0)
                fm.descent += need

            need = ht - (v + fm.bottom - fm.top - istartv)
            if (need > 0)
                fm.bottom += need
        }
    }
}
