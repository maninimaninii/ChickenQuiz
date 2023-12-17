package com.example.chickenquiz.flappy


import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.chickenquiz.flappy.SharedData.Companion.etat

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    lateinit var gameThread: GameThread

    init {
        Initialisation()
        DonneesApp.gv = this
    }

    // fonctions par défaut
    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        // on lance le thread à la création de la surface
        if (!gameThread.isRunning) {
            gameThread = GameThread(surfaceHolder)
            gameThread.start()
        } else {
            gameThread.start()
        }
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
        // Ne rien faire ici
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        // on assure de stopper le thread à la destruction
        if (gameThread.isRunning) {
            gameThread.isRunning = false
            var reessayer = true

            while (reessayer) {
                gameThread.interrupt()
                reessayer = false
            }
        }
    }

    fun stopthread() {
        this.gameThread.isRunning = false
        this.gameThread.interrupt()
        this.gameThread.surfaceHolder.removeCallback(this)
    }

    private fun Initialisation() {
        // creation du thread
        val holder = holder
        holder.addCallback(this)
        isFocusable = true

        gameThread = GameThread(holder)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean { // detection des appuis pour les sauts
        val action = event.action
        // touché d'écran

        if (action == MotionEvent.ACTION_DOWN) {
            etat = 1
            DonneesApp.gameEngine.wazo.setVitesse(DonneesApp.vitessesaut)
        }
        return true
    }
}
