package com.example.pong.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Rect {
    var x : Float = 0f
    var y : Float = 0f
    var s : Float = 0f

    fun update(dx : Float) {
        x += dx - x - s / 2
    }

    fun draw(canvas : Canvas) {
        val white = Paint().apply { color = Color.WHITE }
        canvas.drawRect(x, y, x + s, y + s / 10, white)
    }

    fun setCoordinates(x : Float, y : Float) {
        this.x = x - s / 2
        this.y = y - s / 20
    }

    fun setSize(size : Float) {
        this.s = size
    }
}