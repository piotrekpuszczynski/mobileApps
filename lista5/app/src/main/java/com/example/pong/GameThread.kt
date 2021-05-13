package com.example.pong

import android.view.SurfaceHolder

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView) :
    Thread() {

    private var running = false
    private val targetFPS = 25

    override fun run() {
        var startTime : Long
        var timeMillis : Long
        var waitTime : Long
        val targetTime = (1000 / targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()
            val canvas = surfaceHolder.lockCanvas()
            gameView.draw(canvas)
            gameView.update(canvas)
            gameView.check()
            surfaceHolder.unlockCanvasAndPost(canvas)
            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            if (waitTime > 0)
                sleep(waitTime)
        }
    }

    fun startGame() {
        running = true
        start()
    }

    fun stopGame() {
        running = false
    }
}