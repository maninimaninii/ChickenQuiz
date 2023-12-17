package com.example.chickenquiz.flappy

import android.graphics.Canvas
import android.os.SystemClock
import android.util.Log
import android.view.SurfaceHolder

class GameThread(val surfaceHolder: SurfaceHolder) : Thread() { // thread qui fonctionne avec GameView en utilisant les fonctions de GameEngine

    var isRunning: Boolean = true
    private var startTime: Long = 0
    private var loopTime: Long = 0
    private val DELAY: Long = 33

    override fun run() {

        while (isRunning) {
            startTime = SystemClock.uptimeMillis()

            val canvas = surfaceHolder.lockCanvas(null) // on verrouille afin de pouvoir dessiner dessus
            if (canvas != null) {
                // on appelle les fonctions de dessin contenues dans GameEngine
                DonneesApp.gameEngine.DessinBg(canvas) // on dessine l'arriere-plan
                DonneesApp.gameEngine.DessinOiseau(canvas)// on dessine l'oiseau
                DonneesApp.gameEngine.DessinTuyaux(canvas)// on dessine les tuyaux
                DonneesApp.gameEngine.DessinScore(canvas)// on dessine le score

                surfaceHolder.unlockCanvasAndPost(canvas)// on deverrouille pour afficher
            }

            loopTime = SystemClock.uptimeMillis() - startTime

            if (loopTime < DELAY) { // pour assurer le bon déroulement du thread
                try {
                    Thread.sleep(DELAY - loopTime)
                } catch (e: InterruptedException) {
                    Log.e("Interrupted", "Interrupted while sleeping")
                }
            }
        }
    }





    fun stopThread() {
        isRunning = false
        this.interrupt()
    }
}
