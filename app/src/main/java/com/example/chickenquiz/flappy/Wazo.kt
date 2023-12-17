package com.example.chickenquiz.flappy


import android.graphics.Rect

enum class TypeOiseau {
    WAZO, POULET
}

class Wazo(t: TypeOiseau) {

    private var wazoX: Int
    private var wazoY: Int
    private var frameacc: Int
    private var vitesse: Int
    var type: TypeOiseau
    lateinit var rect: Rect

    init {
        wazoX = DonneesApp.SCREEN_WIDTH / 2 - DonneesApp.bitmapBank.getwazoWidth() / 2
        wazoY = DonneesApp.SCREEN_HEIGHT / 2 - DonneesApp.bitmapBank.getwazoHeight() / 2
        frameacc = 0
        type = t
        vitesse = 0
    }

    fun getVitesse(): Int {
        return vitesse
    }

    fun setVitesse(v: Int) {
        this.vitesse = v
    }

    fun getWazoX(): Int {
        return wazoX
    }

    fun getWazoY(): Int {
        return wazoY
    }

    fun getFrame(): Int {
        return frameacc
    }

    fun setFrame(f: Int) {
        this.frameacc = f
    }

    fun setX(x: Int) {
        this.wazoX = x
    }

    fun setY(y: Int) {
        this.wazoY = y
    }

    fun getOiseauRect(): Rect {
        return Rect(
            wazoX,
            wazoY,
            DonneesApp.bitmapBank.getwazoWidth() + wazoX,
            DonneesApp.bitmapBank.getwazoHeight() + wazoY
        )
    }


}
