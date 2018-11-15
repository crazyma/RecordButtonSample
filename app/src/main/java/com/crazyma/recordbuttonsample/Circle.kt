package com.crazyma.recordbuttonsample

class Circle(var normalRadius: Float, var pressedRadius: Float) {

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

    fun calculateDistance() {
        radiusDistance = normalRadius - pressedRadius
    }
}