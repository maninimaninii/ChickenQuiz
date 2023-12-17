package com.example.chickenquiz.flappy


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import java.util.*

class Tuyau {
    private var tuyauX: Int
    private var tuyauY: Int
    var bm: Bitmap? = null // contrairement à l'oiseau, plusieurs tuyaux sont créés, nous avons donc décidé de placer le Bitmap à l'interieur de la classe

    companion object {
        var vitesse: Int = 0
    }

    constructor() {
        tuyauX = DonneesApp.SCREEN_WIDTH / 2 - DonneesApp.bitmapBank.getwazoWidth() / 2
        tuyauY = DonneesApp.SCREEN_HEIGHT / 2 + DonneesApp.bitmapBank.getwazoHeight() * 2
        vitesse = 10 * DonneesApp.SCREEN_WIDTH / 1000
    }

    constructor(x: Int, y: Int) {
        tuyauX = x
        tuyauY = y
        vitesse = 10 * DonneesApp.SCREEN_WIDTH / 1000
    }

    fun getTuyauX(): Int {
        return tuyauX
    }

    fun getTuyauY(): Int {
        return tuyauY
    }

    fun setTuyauX(tuyauX: Int) {
        this.tuyauX = tuyauX
    }

    fun setTuyauY(tuyauY: Int) {
        this.tuyauY = tuyauY
    }


    fun setbm(b: Bitmap) {
        bm = b
    }

    fun spawnAleatoire() {
        val r = Random()
        this.tuyauY = r.nextInt(100) - 100
    }

    fun getRect(): Rect {
        return Rect(
            tuyauX,
            tuyauY,
            DonneesApp.bitmapBank.gettuyoWidth() + tuyauX,
            DonneesApp.bitmapBank.gettuyoHeigth() + tuyauY
        )
    }

    fun draw(canvas: Canvas) {
        tuyauX -= vitesse
        bm?.let {
            canvas.drawBitmap(it, tuyauX.toFloat(), tuyauY.toFloat(), null)
        }
    }
}
