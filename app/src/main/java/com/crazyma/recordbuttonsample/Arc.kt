package com.crazyma.recordbuttonsample

import android.graphics.RectF

class Arc() {

    companion object {
        const val ANGLE_START = -90f
        const val ANGLE_END = 270f
    }

    var normalRadius = 140f
    var pressedRadius = 150f
    var radiusDistance = 0f
    var currentRadius = normalRadius
    var centerX = 0
    var centerY = 0
    var currentRectF: RectF = RectF(0f, 0f, 0f, 0f)

    var angle = 0f
    val positiveStart = ANGLE_START
    var positiveSweep = angle
        get() = angle
    var negativeStart = angle + ANGLE_START
    var negativeSweep = ANGLE_END - negativeStart

    init {
        calculateDistance()
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

    private fun calculateDistance() {
        radiusDistance = normalRadius - pressedRadius
        calculateRectF()
    }

    private fun calculateRectF() {
        currentRectF.left = centerX - currentRadius
        currentRectF.top = centerY - currentRadius
        currentRectF.right = centerX + currentRadius
        currentRectF.bottom = centerY + currentRadius
    }
}