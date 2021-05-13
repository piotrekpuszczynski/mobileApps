package com.example.pong

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.pong.shapes.Ball
import com.example.pong.shapes.Rect

class GameView(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    private val thread : GameThread
    private val ball = Ball()
    private var playerOne = Rect()
    private var playerTwo = Rect()
    private var playerOnePoints = 0
    private var playerTwoPoints = 0
    private val pointsToWin = 5

    private var dx = 0f
    private var dy = 0f

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceChanged(holder : SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder : SurfaceHolder) {
        thread.stopGame()
    }

    override fun surfaceCreated(holder : SurfaceHolder) {
        val size = (width / 8).toFloat()
        val horizontalCenter = (width / 2).toFloat()
        val verticalCenter = (height / 2).toFloat()
        val playerY = (height / 10).toFloat()

        playerOne.setSize(size)
        playerOne.setCoordinates(horizontalCenter, playerY * 9)

        playerTwo.setSize(size)
        playerTwo.setCoordinates(horizontalCenter, playerY)

        ball.setStart(verticalCenter)
        ball.setSize(size / 4)

        dx = (width / 300).toFloat()
        dy = (height / 300).toFloat()

        thread.startGame()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.y!! > height / 2) {
            playerOne.update(event.x)
        } else {
            playerTwo.update(event.x)
        }

        return true
    }

    override fun draw(canvas : Canvas?) {
        super.draw(canvas)

        if (canvas == null) return

        val white = Paint().apply {
            color = Color.WHITE
            textSize = (width / 3).toFloat()
            alpha = 80
        }

        canvas.drawLine(0f, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(), white)
        canvas.drawText(playerOnePoints.toString(),
            (width / 10).toFloat(), ((height / 2) + width / 3).toFloat(), white)
        canvas.drawText(playerTwoPoints.toString(),
            (width / 10).toFloat(), (height / 2 - width / 10).toFloat(), white)

        ball.draw(canvas)
        playerOne.draw(canvas)
        playerTwo.draw(canvas)
    }

    fun update(canvas : Canvas) {
        if (ball.x < 0 || ball.x + ball.s > canvas.width) dx = -dx

        if ((ball.y < playerTwo.y + playerTwo.s / 10 && ball.y > playerTwo.y - dy - 1 &&
                    ball.x + ball.s / 2 < playerTwo.x + playerTwo.s && ball.x + ball.s / 2 > playerTwo.x) ||
            (ball.y + ball.s > playerOne.y && ball.y + ball.s < playerOne.y + dy + 1 &&
                    ball.x + ball.s / 2 < playerOne.x + playerOne.s && ball.x + ball.s / 2 > playerOne.x)) dy = -dy

        ball.update(dx, dy)
    }

    fun check() {
        if (ball.y < 0) {
            playerOnePoints++
            ball.setStart((height / 2).toFloat())
        } else if (ball.y > height) {
            playerTwoPoints++
            ball.setStart((height / 2).toFloat())
        }

        if (playerOnePoints == pointsToWin) {

        } else if (playerTwoPoints == pointsToWin) {

        }
    }
}