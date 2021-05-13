package com.example.pong.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Ball {
    var x : Float = 0f
    var y : Float = 0f
    var s : Float = 0f

    fun update(dx : Float, dy : Float) {
        x += dx
        y += dy
    }

    fun draw(canvas : Canvas) {
        val white = Paint().apply { color = Color.WHITE }
        canvas.drawOval(x, y, x + s, y + s, white)
    }

    fun setStart(y : Float) {
        this.y = y
    }

    fun setSize(size : Float) {
        this.s = size
    }
}