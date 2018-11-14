package com.crazyma.recordbuttonsample

import android.graphics.Paint

class Circle() {

    var normalRadius = 120f
    var pressedRadius = 90f
    var radiusDistance = 0f
    var currentRadius = normalRadius
    var centerX = 0
    var centerY = 0

    var paint: Paint? = null

    init{
        calculateDistance()
    }

    fun calculateCurrentValue(value: Float){

        currentRadius = pressedRadius + radiusDistance * (1f - value)
    }

    private fun calculateDistance(){
        radiusDistance = normalRadius - pressedRadius
    }


}