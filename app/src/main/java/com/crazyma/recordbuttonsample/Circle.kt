package com.crazyma.recordbuttonsample

class Circle(var normalRadius: Float, var pressedRadius: Float) {


    var currentRadius = normalRadius
    var centerX = 0
    var centerY = 0

    private var radiusDistance = 0f

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