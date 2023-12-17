package com.example.chickenquiz

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chickenquiz.flappy.SharedData.Companion.attente

class MainActivity : AppCompatActivity() {

    private lateinit var gameManager: GameManager
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var dbHelper: QuizDatabaseHelper
    private lateinit var name: String



    private val PREF_KEY_SOUND_PLAYED = "SOUND_PLAYED"

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = QuizDatabaseHelper(this)
         name = intent.getStringExtra("name").toString()
        //on recupere le nom
        dbHelper.initializeQuizQuestions()

        val pd = name?.let { dbHelper.getPlayerData(it) } //on recupere les données du joueur


        attente = true


        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        gameManager = GameManager(sharedPreferences)
        if (pd != null) {
            gameManager.maxUnlockedLevel = pd.maxLevel //niveay max débloqué avant
            updateLevelButtons()  //on debloque les boutons
        }
        gameManager.lastCompletedLevel = 0


        startMusic()

        // Récupération des ImageButton
        val level1Button = findViewById<View>(R.id.lvl1Button)

        // Ajout d'un écouteur de clic pour chaque bouton
        level1Button.setOnClickListener {
            onLevelButtonClick(1, AnswerType.TEXT)
        }
    }

    override fun onResume() {
        super.onResume()
        startMusic()
        updateLevelButtons()
    }



    private fun startQuizLevel(level: Int, questionType: AnswerType) {
        stopMusic()
        val intent = Intent(this, LevelQuizActivity::class.java)
        intent.putExtra("LEVEL", level)
        intent.putExtra("QUESTION_TYPE", questionType)
        startActivity(intent)
    }

    private fun onLevelButtonClick(level: Int, type: AnswerType) {
        if (level <= gameManager.maxUnlockedLevel) {
            // Le niveau est déverrouillé, lancez le quiz pour ce niveau
            stopMusic()
            startQuizLevel(level, type)
        } else {
            // Le niveau n'est pas encore déverrouillé
            val message = "Niveau verrouillé. Terminez les niveaux précédents pour le déverrouiller."
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLevelButtons() {
        val levelButtons = listOf(
            findViewById<View>(R.id.lvl1Button),
            findViewById<View>(R.id.lvl2Button),
            findViewById<View>(R.id.lvl3Button),
            findViewById<View>(R.id.lvl4Button)
        )

        for ((index, levelButton) in levelButtons.withIndex()) {
            levelButton.isEnabled = true

            levelButton.setOnClickListener {
                if ((index + 1) <= gameManager.maxUnlockedLevel) {
                    dbHelper.updatePlayerMaxLevel(name, gameManager.maxUnlockedLevel)
                    startQuizLevel(index + 1, getQuestionTypeForLevel(index + 1))
                } else {
                    // Le niveau n'est pas déverrouillé
                    val message =
                        "Niveau verrouillé. Terminez les niveaux précédents pour le déverrouiller."
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getQuestionTypeForLevel(level: Int): AnswerType {
        return when (level) {
            4 -> AnswerType.IMAGE
            else -> AnswerType.TEXT
        }
    }

    private fun startMusic() {
        if (!::mediaPlayer.isInitialized || !mediaPlayer.isPlaying) {
            mediaPlayer = MediaPlayer.create(this, R.raw.musik)
            mediaPlayer.isLooping = true

            // Ajustez le volume du système
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?
            audioManager?.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0
            )

            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

    private fun stopMusic() {
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }
}