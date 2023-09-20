package com.skit.cis

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout

class InnerShadowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        const val SHADOW_RADIUS = 20f
        const val STROKE_WIDTH = 20f
    }

    private val shadowPaint: Paint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = STROKE_WIDTH
            color = Color.RED
            setShadowLayer(SHADOW_RADIUS, 0f, 0f, Color.BLACK)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        shadowLineRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    private var shadowLineRect: RectF? = null
    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas ?: return
        drawInnerShadow(canvas)
    }

    private fun drawInnerShadow(canvas: Canvas) {
        shadowLineRect?.let { shadowLineRect ->
            canvas.save()
            val inset = -(STROKE_WIDTH / 2)
            shadowLineRect.inset(inset, inset)
            val clipRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
            canvas.clipRect(clipRect)
            canvas.drawRect(shadowLineRect, shadowPaint)
            canvas.restore()
        }
    }
}