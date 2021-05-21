package com.example.pong.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.view.MotionEventCompat
import androidx.room.Room
import com.example.pong.database.AppDatabase
import com.example.pong.shapes.Ball
import com.example.pong.shapes.Rect
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class GameView(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    private lateinit var database : AppDatabase
    private val thread : GameThread
    private val ball = Ball()
    private var playerOne = Rect()
    private var playerTwo = Rect()
    private var playerOnePoints = 0
    private var playerTwoPoints = 0

    companion object {
        const val POINTS = 3
    }

    private var dx = 0f
    private var dy = 0f

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceChanged(holder : SurfaceHolder, format: Int, width: Int, height: Int) {}

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
        if (thread.getRunning()) {
            if (event?.y!! > height / 2) {
                playerOne.update(event.x)
            } else {
                playerTwo.update(event.x)
            }
        } else if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {

            GlobalScope.launch {
                try {
                    database = Room.databaseBuilder(
                        (context as Activity),
                        AppDatabase::class.java,
                        "players.db"
                    ).build()

                    database.playerDao().updatePoints("player one", playerOnePoints)
                    database.playerDao().updatePoints("player two", playerTwoPoints)
                    if (playerOnePoints == POINTS) {
                        database.playerDao().updateWinner("player one", 1)
                    } else {
                        database.playerDao().updateWinner("player two", 1)
                    }

                } catch (e: Exception) {}
            }
            (context as Activity).finish()
        }
        return true
    }

    override fun draw(canvas : Canvas?) {
        super.draw(canvas)

        if (canvas == null) return
        
        var white = Paint().apply {
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

        if (!thread.getRunning()) {

            white = Paint().apply {
                color = Color.WHITE
                textSize = (width / 8).toFloat()
                textAlign = Paint.Align.CENTER
            }

            val player : String = if (playerTwoPoints == POINTS) "Player two"
            else "Player one"

            canvas.drawText("$player won", (width / 2).toFloat(), (height / 2).toFloat(), white)

            white = Paint().apply {
                color = Color.WHITE
                textSize = (width / 18).toFloat()
                textAlign = Paint.Align.CENTER
                alpha = 150
            }
            canvas.drawText("click anywhere to continue", (width / 2).toFloat(), (height / 2 + width / 14).toFloat(), white)

        }
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
        } else if (ball.y + ball.s > height) {
            playerTwoPoints++
            ball.setStart((height / 2).toFloat())
        }

        if (playerOnePoints == POINTS || playerTwoPoints == POINTS) {
            thread.stopGame()
        }
    }
}