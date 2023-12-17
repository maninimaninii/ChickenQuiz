package com.example.chickenquiz.flappy

import android.content.Context
import android.media.MediaPlayer
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import com.example.chickenquiz.R

class DonneesApp {
    companion object {
        lateinit var bitmapBank: BitmapBank // Contient toutes les images qui seront utilisées

        lateinit var gv: GameView

        lateinit var gameEngine: GameEngine // Contient les fonctions qui gèrent le fonctionnement du jeu

        var SCREEN_WIDTH: Int = 0
        var SCREEN_HEIGHT: Int = 0 // Les constantes de taille de l'écran

        var gravite: Int = 0 // Gravité, vitesse de chute
        var vitessesaut: Int = 0 // Vitesse gagnée grâce à un saut

        lateinit var gameacContext: Context // Contexte de l'activité où se déroule le jeu
        lateinit var mp: MediaPlayer // Musique de fond

        fun initialisation(context: Context) {
            setScreenSize(context)
            bitmapBank = BitmapBank(context.resources)
            gameEngine = GameEngine()
            gravite = 3
            vitessesaut = -30
        }


        fun startmusc() { // Lancer la musique et la faire se répéter
            mp = MediaPlayer.create(gameacContext, R.raw.musique).apply { isLooping = true }
            mp.start()
        }

        fun stopmusc() { // Arrêter la musique à l'arrêt du jeu
            mp.stop()
        }

        private fun setScreenSize(context: Context) { // Permet de calibrer à l'écran actuel
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dis = wm.defaultDisplay
            val metrics = DisplayMetrics()
            dis.getMetrics(metrics)
            val width = metrics.widthPixels
            val height = metrics.heightPixels

            SCREEN_WIDTH = width
            SCREEN_HEIGHT = height
        }
    }
}
