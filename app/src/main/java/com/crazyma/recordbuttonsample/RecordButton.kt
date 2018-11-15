package com.crazyma.recordbuttonsample

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.IntDef
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator

class RecordButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val DURATION_PRESS_ANIMATION = 500L
        const val RECORD_MAX_DURATION = 5000L
    }

    object Touch {
        const val STATE_NORMAL = 0
        const val STATE_PRESS = 1
        const val STATE_RECORD = 2

        @Retention(AnnotationRetention.SOURCE)
        @IntDef(STATE_NORMAL, STATE_PRESS, STATE_RECORD)
        annotation class STATE
    }

    @Touch.STATE
    var state = Touch.STATE_NORMAL

    private var currentRadiusValue: Float = 0f
    private var centerX = 0
    private var centerY = 0
    private var normalColor = Color.BLACK
    private var pressedColor = Color.RED

    private var pressAnimator: ValueAnimator? = null
    private var recordAnimator: ValueAnimator? = null

    private lateinit var circle: Circle
    private lateinit var arc: Arc

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.RecordButton,
                0, 0).apply {

            try {

                circle = Circle(
                        getDimension(R.styleable.RecordButton_normalCircleRadius, 120f),
                        getDimension(R.styleable.RecordButton_pressedCircleRadius, 90f)
                )

                arc = Arc(
                        getDimension(R.styleable.RecordButton_normalArcRadius, 140f),
                        getDimension(R.styleable.RecordButton_pressedArcRadius, 150f)
                )

                arcPaint.strokeWidth = getDimension(R.styleable.RecordButton_strokeWidth, 12f)
                normalColor = getColor(R.styleable.RecordButton_normalColor, Color.BLACK)
                pressedColor = getColor(R.styleable.RecordButton_pressedColor, Color.RED)
            } finally {
                recycle()
            }
        }

        circlePaint.color = normalColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = Math.max(w, h) / 2
        centerY = centerX

        arc.setCenter(centerX, centerY)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        arcPaint.color = pressedColor
        canvas.drawArc(arc.currentRectF, arc.positiveStart, arc.positiveSweep, false, arcPaint)
        arcPaint.color = normalColor
        canvas.drawArc(arc.currentRectF, arc.negativeStart, arc.negativeSweep, false, arcPaint)

        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), circle.currentRadius, circlePaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                enterPressState()
                true
            }
            MotionEvent.ACTION_UP -> {
                exitPressState()
                true
            }
            else -> super.onTouchEvent(event)
        }

    }

    private fun runRecordAnim() {
        recordAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = RECORD_MAX_DURATION
            interpolator = LinearInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Float
                arc.calculateAngle(value)
                postInvalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {

                    postInvalidate()
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {
                    state = Touch.STATE_RECORD
                }
            })
        }.apply { start() }
    }

    private fun enterPressState() {

        if (recordAnimator?.isRunning == true) {
            recordAnimator!!.cancel()
        }

        if (pressAnimator?.isRunning == true) {
            pressAnimator!!.cancel()
        }

        pressAnimator = ValueAnimator.ofFloat(currentRadiusValue, 1f).apply {
            duration = DURATION_PRESS_ANIMATION
            interpolator = LinearInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Float
                currentRadiusValue = value
                circle.calculateCurrentValue(value)
                arc.calculateCurrentValue(value)
                postInvalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    if (state == Touch.STATE_PRESS) {
                        state = Touch.STATE_RECORD
                        runRecordAnim()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                    state = Touch.STATE_NORMAL
                }

                override fun onAnimationStart(animation: Animator?) {
                    state = Touch.STATE_PRESS
                }
            })
        }.apply { start() }
    }

    private fun exitPressState() {
        if (recordAnimator?.isRunning == true) {
            recordAnimator!!.cancel()

        }

        if (pressAnimator?.isRunning == true) {
            pressAnimator!!.cancel()
        }

        arc.calculateAngle(0f)
        pressAnimator = ValueAnimator.ofFloat(currentRadiusValue, 0f).apply {
            duration = DURATION_PRESS_ANIMATION
            interpolator = LinearInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Float
                currentRadiusValue = value
                circle.calculateCurrentValue(value)
                arc.calculateCurrentValue(value)
                postInvalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {}

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {
                    state = Touch.STATE_NORMAL
                }
            })
        }.apply { start() }
    }

}