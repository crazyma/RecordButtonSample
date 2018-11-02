package com.crazyma.recordbuttonsample

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class RecordButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val SMALL_CIRCLE_OFFSET = 24
        const val LARGE_CIRCLE_OFFSET = 16
        const val STROKE_WIDTH = 8
        const val ANGLE_START = -90f
        const val ANGLE_END = 270f
    }

    lateinit var largeCircleRectF: RectF
    var smallCircleRadius = 0f
    var centerX = 0
    var centerY = 0
    var percent = .5f
    val angle = 0f

    private val dp = Resources.getSystem().displayMetrics.density
    private val smallPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    private val largePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = STROKE_WIDTH * dp
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = Math.max(w, h) / 2
        centerY = centerX
        smallCircleRadius = centerX - SMALL_CIRCLE_OFFSET * dp

        val largeCircleRadius = centerX - LARGE_CIRCLE_OFFSET * dp
        largeCircleRectF = RectF(centerX - largeCircleRadius, centerY - largeCircleRadius, centerX + largeCircleRadius, centerY + largeCircleRadius)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // TODO: should move from onDraw
        val secondStart = angle + ANGLE_START
        val secondSweep = ANGLE_END - secondStart

        canvas.drawArc(largeCircleRectF, ANGLE_START, angle, false, largePaint)
        largePaint.color = Color.RED
        canvas.drawArc(largeCircleRectF, secondStart, secondSweep, false, largePaint)

        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), smallCircleRadius, smallPaint)
    }

}