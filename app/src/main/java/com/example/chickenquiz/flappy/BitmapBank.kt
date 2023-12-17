package com.example.chickenquiz.flappy

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.chickenquiz.R

class BitmapBank(resources: Resources) { // Classe faisant office de stock d'images

    var background_game: Bitmap // Bitmap contenant l'arrière-plan pendant le jeu
    var wazos: Array<Bitmap?> // Tableau de bitmaps contenant les frames d'oiseau
    var tuyaux: Array<Bitmap?> // Tableau de bitmaps contenant les frames de tuyau
    var poulets: Array<Bitmap?> // Tableau de bitmaps contenant les frames de poulet
    var score: Array<Bitmap?> // Tableau de bitmaps contenant les chiffres du score

    init {
        background_game = BitmapFactory.decodeResource(resources, R.drawable.backgroundgame)
        background_game = scaleImage(background_game)

        wazos = arrayOfNulls(12)
        wazos[0] = BitmapFactory.decodeResource(resources, R.drawable.lwazo1)
        wazos[1] = BitmapFactory.decodeResource(resources, R.drawable.lwazo2)
        wazos[2] = BitmapFactory.decodeResource(resources, R.drawable.lwazo3)
        wazos[3] = BitmapFactory.decodeResource(resources, R.drawable.lwazo2)
        wazos[4] = BitmapFactory.decodeResource(resources, R.drawable.lwazobleu1)
        wazos[5] = BitmapFactory.decodeResource(resources, R.drawable.lwazobleu2)
        wazos[6] = BitmapFactory.decodeResource(resources, R.drawable.lwazobleu3)
        wazos[7] = BitmapFactory.decodeResource(resources, R.drawable.lwazobleu2)
        wazos[8] = BitmapFactory.decodeResource(resources, R.drawable.lwazorouj1)
        wazos[9] = BitmapFactory.decodeResource(resources, R.drawable.lwazorouj2)
        wazos[10] = BitmapFactory.decodeResource(resources, R.drawable.lwazorouj3)
        wazos[11] = BitmapFactory.decodeResource(resources, R.drawable.lwazorouj2)

        poulets = arrayOfNulls(2)
        poulets[0] = BitmapFactory.decodeResource(resources, R.drawable.poulet1)
        poulets[1] = BitmapFactory.decodeResource(resources, R.drawable.poulet2)

        tuyaux = arrayOfNulls(6)

        tuyaux[0] = BitmapFactory.decodeResource(resources, R.drawable.pipe1)
        tuyaux[0] = tuyaux[0]?.let { scalepipe0(it) }
        tuyaux[1] = BitmapFactory.decodeResource(resources, R.drawable.pipe2)
        tuyaux[1] = tuyaux[1]?.let { scalepipe1(it) }

        score = arrayOfNulls(10)
        score[0] = BitmapFactory.decodeResource(resources, R.drawable.zero)
        score[1] = BitmapFactory.decodeResource(resources, R.drawable.un)
        score[2] = BitmapFactory.decodeResource(resources, R.drawable.deux)
        score[3] = BitmapFactory.decodeResource(resources, R.drawable.trois)
        score[4] = BitmapFactory.decodeResource(resources, R.drawable.quatre)
        score[5] = BitmapFactory.decodeResource(resources, R.drawable.cinq)
        score[6] = BitmapFactory.decodeResource(resources, R.drawable.six)
        score[7] = BitmapFactory.decodeResource(resources, R.drawable.sept)
        score[8] = BitmapFactory.decodeResource(resources, R.drawable.huit)
        score[9] = BitmapFactory.decodeResource(resources, R.drawable.neuf)
    }

    fun getBird(frame: Int): Bitmap? {
        return wazos[frame]
    }

    fun getPoulet(frame: Int): Bitmap? {
        return poulets[frame]
    }

    fun getTuyo(f: Int): Bitmap? {
        return tuyaux[f]
    }

    fun getwazoWidth(): Int {
        return wazos[0]?.width ?: 0
    }

    fun getwazoHeight(): Int {
        return wazos[0]?.height ?: 0 / 3
    }

    fun gettuyoWidth(): Int {
        return tuyaux[0]?.width ?: 0
    }

    fun getscore(score: Int): Bitmap? {
        return this.score[score]
    }

    fun gettuyoHeigth(): Int {
        return tuyaux[0]?.height ?: 0
    }

    fun getBackground(): Bitmap? {
        return background_game
    }

    fun getBackgroundWidth(): Int {
        return background_game.width
    }

    fun getBackgroundHeight(): Int {
        return background_game.height
    }

    private fun scaleImage(bitmap: Bitmap): Bitmap {
        val widthHeightRatio = getBackgroundWidth() / getBackgroundHeight().toFloat()
        val backgroundScaleWidth = (widthHeightRatio * DonneesApp.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap, backgroundScaleWidth, DonneesApp.SCREEN_HEIGHT, false)
    }

    private fun scalepipe0(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bm, gettuyoWidth() / 4, gettuyoHeigth() / 2 - 200, true)
    }

    private fun scalepipe1(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bm, gettuyoWidth(), gettuyoHeigth(), true)
    }
}
