package com.didchain.didcard.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import com.didchain.android.lib.utils.dp
import com.didchain.didcard.R

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class QRImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2.dp
        context.resources.getColor(android.R.color.transparent, null)
        style = Paint.Style.STROKE
    }

    init {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.qrimageview)
        val color = obtainStyledAttributes.getColor(R.styleable.qrimageview_line_color, context.resources.getColor(R.color.yellow, null))
        paint.color = color
        obtainStyledAttributes.recycle()
    }


    fun setLineColor(color: Int) {
        paint.color = color
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        rect.inset(1.dp, 1.dp)
        canvas.drawRoundRect(rect, 2.dp, 2.dp, paint)
    }
}