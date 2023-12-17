package com.example.chickenquiz.flappy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DonneesApp.initialisation(applicationContext) // Initialisation
        DonneesApp.gameacContext = this // On assigne le contexte de l'activité
        //DonneesApp.startmusc()
        gameView = GameView(this)
        setContentView(gameView)
    }

    fun stop() {
        gameView.gameThread.stop()
    }
}
