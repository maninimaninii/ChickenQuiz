package com.example.chickenquiz

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.random.Random

class ErrorScreenActivity : AppCompatActivity() { //activité qui s'affiche quand erreur non fatale
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val randomLayout = Random.nextInt(2)

        // Sélectionner le layout en fonction du nombre aléatoire
        if (randomLayout == 0) {
            setContentView(R.layout.activity_error_screen2)
        } else {
            setContentView(R.layout.activity_error_screen)
        }
    startShortSound()
        // Retarder le retour à la question du quiz après 3 secondes
        android.os.Handler().postDelayed({
            finish()
        }, 750)
    }

    private fun startShortSound() {
        val shortMediaPlayer = MediaPlayer.create(this, R.raw.fauxx)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        audioManager?.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            0
        )
        shortMediaPlayer.start()
        shortMediaPlayer.setOnCompletionListener { player -> player.release() }

    }
}