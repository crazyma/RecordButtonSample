package com.crazyma.recordbuttonsample

class Circle(var normalRadius: Float = 120f, var pressedRadius: Float = 90f) {

    var radiusDistance = 0f
    var currentRadius = normalRadius
    var centerX = 0
    var centerY = 0

    init {
        calculateDistance()
    }

    fun calculateCurrentValue(value: Float) {
        currentRadius = pressedRadius + radiusDistance * (1f - value)
    }

    private fun calculateDistance() {
        radiusDistance = normalRadius - pressedRadius
    }
}