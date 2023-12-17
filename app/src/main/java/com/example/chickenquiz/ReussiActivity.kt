package com.example.chickenquiz

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.chickenquiz.flappy.GameActivity



class ReussiActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reussi)

        startShortSound()
        // Retarder le retour à la question du quiz après 3 secondes
        android.os.Handler().postDelayed({
            finish()
        }, 1700)

    }

    private fun startShortSound() {
        val shortMediaPlayer = MediaPlayer.create(this, R.raw.algerie)
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