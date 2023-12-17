package com.example.chickenquiz.flappy


class BackgroundImage { // Classe qui gère l'image de fond
    private var x: Int = 0
    private var y: Int = 0
    private var velocite: Int = 0

    constructor() {
        x = 0
        y = 0
    }

    fun getX(): Int {
        return x
    }

    fun getY(): Int {
        return y
    }

    fun setX(x: Int) {
        this.x = x
    }

    fun setY(y: Int) {
        this.y = y
    }
}
