package com.example.pong.game

import android.view.SurfaceHolder

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView) :
    Thread() {

    private var running = false

    companion object {
        const val FPS = 50
    }

    override fun run() {
        var startTime : Long
        var timeMillis : Long
        var waitTime : Long
        val targetTime = (1000 / FPS).toLong()

        while (running) {
            startTime = System.nanoTime()
            val canvas = surfaceHolder.lockCanvas()
            gameView.check()
            gameView.draw(canvas)
            gameView.update(canvas)
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

    fun getRunning() : Boolean {
        return running
    }
}