package com.example.chickenquiz
import android.content.SharedPreferences
import android.media.MediaPlayer

class GameManager(private val sharedPreferences: SharedPreferences) {  //classe qui sauvegarde les infos des niveaux à travers la partie




    var maxUnlockedLevel: Int
        get() = sharedPreferences.getInt("MAX_UNLOCKED_LEVEL", 1)
        set(value) {
            sharedPreferences.edit().putInt("MAX_UNLOCKED_LEVEL", value).apply()
        }

    var lastCompletedLevel: Int
        get() = sharedPreferences.getInt("LAST_COMPLETED_LEVEL", 0)
        set(value) {
            sharedPreferences.edit().putInt("LAST_COMPLETED_LEVEL", value).apply()
        }

    fun saveMaxUnlockedLevel() {
        sharedPreferences.edit().putInt("MAX_UNLOCKED_LEVEL", maxUnlockedLevel).apply()
    }

    fun saveLastCompletedLevel() {
        sharedPreferences.edit().putInt("LAST_COMPLETED_LEVEL", lastCompletedLevel).apply()
    }





}



