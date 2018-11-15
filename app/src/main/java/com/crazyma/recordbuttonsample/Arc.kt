package com.crazyma.recordbuttonsample

import android.graphics.RectF

class Arc(var normalRadius: Float, var pressedRadius: Float) {

    companion object {
        const val ANGLE_START = -90f
        const val ANGLE_END = 270f
    }

    private var radiusDistance = 0f
    private var currentRadius = normalRadius
    private var centerX = 0
    private var centerY = 0
    private var angle = 0f

    val positiveStart = ANGLE_START
    var positiveSweep = angle
        get() = angle
    var negativeStart = angle + ANGLE_START
    var negativeSweep = ANGLE_END - negativeStart

    var currentRectF: RectF = RectF(0f, 0f, 0f, 0f)

    init {
        calculateDistance()
        calculateRectF()
    }

    fun calculateCurrentValue(value: Float) {
        currentRadius = pressedRadius + radiusDistance * (1f - value)
        calculateRectF()
    }

    fun calculateAngle(value: Float) {
        angle = value * 360f
        negativeStart = angle + ANGLE_START
        negativeSweep = ANGLE_END - negativeStart
    }

    fun setCenter(centerX: Int, centerY: Int) {
        this.centerX = centerX
        this.centerY = centerY
        calculateRectF()
    }

    fun calculateDistance() {
        radiusDistance = normalRadius - pressedRadius
    }

    private fun calculateRectF() {
        currentRectF.left = centerX - currentRadius
        currentRectF.top = centerY - currentRadius
        currentRectF.right = centerX + currentRadius
        currentRectF.bottom = centerY + currentRadius
    }
}