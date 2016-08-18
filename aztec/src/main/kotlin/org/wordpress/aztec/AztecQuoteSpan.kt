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

package org.wordpress.aztec

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.text.Layout
import android.text.style.LineBackgroundSpan
import android.text.style.ParagraphStyle
import android.text.style.QuoteSpan
import android.text.style.ReplacementSpan
import android.util.TypedValue

class AztecQuoteSpan : ReplacementSpan, ParagraphStyle {

    private var quoteBackground: Int = 0
    private var quoteColor: Int = 0
    private var quoteMargin: Int = 0
    private var quotePadding: Int = 0
    private var quoteWidth: Int = 0

    constructor(quoteBackground: Int, quoteColor: Int, quoteMargin: Int, quoteWidth: Int, quotePadding: Int) {
        this.quoteBackground = quoteBackground
        this.quoteColor = quoteColor
        this.quoteMargin = quoteMargin
        this.quoteWidth = quoteWidth
        this.quotePadding = quotePadding
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return quotePadding + paint.measureText(text.subSequence(start, end).toString()).toInt() + quotePadding
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val width = paint.measureText(text.subSequence(start, end).toString())
        val rect = RectF(x, top.toFloat() - quotePadding, canvas.width.toFloat(), bottom.toFloat() + quotePadding)
        paint.color = quoteBackground
        canvas.drawRoundRect(rect, 10f, 10f, paint)

        val fm = paint.fontMetrics
        val h = Math.ceil((fm.descent - fm.ascent).toDouble()).toInt();
        val i = h - fm.descent
        paint.color = Color.BLACK
        canvas.drawText(text, start, end, x + quotePadding, i + rect.top + quotePadding, paint)
    }

//
//    override fun getLeadingMargin(first: Boolean): Int {
//        return quoteMargin + quoteWidth + quotePadding
//    }
//
//    override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
//                                   top: Int, baseline: Int, bottom: Int,
//                                   text: CharSequence, start: Int, end: Int,
//                                   first: Boolean, layout: Layout) {
//        val style = p.style
//        val color = p.color
//
//        p.style = Paint.Style.FILL
//        p.color = quoteColor
//        c.drawRect(x.toFloat() + quoteMargin, top.toFloat(), (x + quoteMargin + dir * quoteWidth).toFloat(), bottom.toFloat(), p)
//
//        p.style = style
//        p.color = color
//    }
//
//    override fun drawBackground(c: Canvas, p: Paint, left: Int, right: Int,
//                                top: Int, baseline: Int, bottom: Int,
//                                text: CharSequence?, start: Int, end: Int,
//                                lnum: Int) {
//        val paintColor = p.color
//        p.color = quoteBackground
//        c.drawRect(left.toFloat() + quoteMargin, top.toFloat(), right.toFloat(), bottom.toFloat(), p)
//        p.color = paintColor
//    }
}
