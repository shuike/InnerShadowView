package com.skit.cis

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout

class InnerShadowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var shadowLineRect: RectF? = null
    private var shadowColor: Int = Color.BLACK
    private val shadowPaint: Paint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = STROKE_WIDTH
            color = Color.RED
        }
    }

    var round = 0f
        private set

    fun setRound(round: Float) {
        this.round = round
        invalidate()
        invalidateOutline()
    }

    init {
        initAttrs(attrs, context)
        initPaints()
        initOutlineProvider()
    }

    private fun initOutlineProvider() {
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                view ?: return
                outline ?: return
                return outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, round)
            }
        }
    }

    private fun initPaints() {
        shadowPaint.setShadowLayer(SHADOW_RADIUS, 0f, 0f, shadowColor)
    }

    private fun initAttrs(attrs: AttributeSet?, context: Context) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InnerShadowView)
        round = typedArray.getDimension(R.styleable.InnerShadowView_round, 0f)
        shadowColor = typedArray.getColor(R.styleable.InnerShadowView_shadowColor, Color.BLACK)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        shadowLineRect =
            RectF(RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())).also {
                val inset = -(STROKE_WIDTH / 2f)
                it.inset(inset, inset)
            }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas ?: return
        drawInnerShadow(canvas)
    }

    private fun drawInnerShadow(canvas: Canvas) {
        canvas.save()
        shadowLineRect?.let { canvas.drawRoundRect(it, round, round, shadowPaint) }
        canvas.restore()
    }

    companion object {
        const val SHADOW_RADIUS = 20f
        const val STROKE_WIDTH = 20f
    }
}