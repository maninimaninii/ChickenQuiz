package com.example.chickenquiz.flappy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chickenquiz.R

class FlappyMainGameActivity : AppCompatActivity() { // Écran d'accueil

    private lateinit var playb: ImageView // Bouton de lancement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flappy_main_game)

        DonneesApp.initialisation(applicationContext) // Initialisation
        playb = findViewById(R.id.play) // On relie la variable au bouton

        playb.setOnClickListener(View.OnClickListener { // Lancement du jeu en appuyant sur play
            val intent = Intent(this@FlappyMainGameActivity, GameActivity::class.java)
            startActivity(intent) // Lancement de l'activité de jeu
            finish() // On arrête l'activité d'accueil
        })
    }
}