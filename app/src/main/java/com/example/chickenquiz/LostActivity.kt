package com.example.chickenquiz

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost)
        startShortSound()


        // Retarder le retour à la question du quiz après 3 secondes
        android.os.Handler().postDelayed({
            finish()
        }, 3000)
    }

    private fun startShortSound() {
        val shortMediaPlayer = MediaPlayer.create(this, R.raw.decu)
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