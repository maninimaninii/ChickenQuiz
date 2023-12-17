package com.example.chickenquiz

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class NameActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var playerListView: ListView
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var dbHelper: QuizDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        editText = findViewById(R.id.editText)
        playerListView =
            findViewById(R.id.playerListView)

        dbHelper = QuizDatabaseHelper(this)
        dbHelper.initializeQuizQuestions()

        // on charge les joeurs
        val players = dbHelper.getAllPlayers().toMutableList()
        startShortSound()

        if (players.isNotEmpty()) {
            // la liste de joueurs est selectionnable, elle charge la progression de ce dernier
            playerAdapter = PlayerAdapter(players) { playerName ->
                startMainActivity(playerName)
            }

            playerListView.adapter = playerAdapter
        }

        val sendButton: Button = findViewById(R.id.envoi)
        val emptyButton: Button = findViewById(R.id.supp)

        sendButton.setOnClickListener {
            val inputText = editText.text.toString()
            if(inputText.isEmpty()){
                showEmptyFieldDialog()}else{

            dbHelper.insertPlayer(inputText)
            startMainActivity(inputText)
        }}

        emptyButton.setOnClickListener {
            dbHelper.deleteAllPlayers()
            playerAdapter.clear()
            playerAdapter.notifyDataSetChanged()
        }
    }

    private fun startMainActivity(playerName: String) {


        val intent = Intent(this@NameActivity, MainActivity::class.java)
        // Passer le nom to MainActivity
        intent.putExtra("name", playerName)
        startActivity(intent)
        finish()
    }

    private fun showEmptyFieldDialog() {   //boite de dialogue si la tache ajoutée est vide
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Champ Vide")
        builder.setMessage("Veuillez entrer un nom valide .")
        builder.setPositiveButton("OK", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun startShortSound() {
        val shortMediaPlayer = MediaPlayer.create(this, R.raw.coq)
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
